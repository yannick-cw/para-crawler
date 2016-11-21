package parsing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import parsing.helpers.ParseResultsOps.ParseResultsImproved
import parsing.helpers.{ParaMail, Protocols, Requester}
import parsing.models.{FacebookResults, User}
import parsing.web_connectors.{Connector, DhvConnector, FacebookConnector}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import parsing.config.Config._

object Scheduler extends App with Requester with Protocols {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  var connectors: List[Connector] = List(
    FacebookConnector(0, accessToken = token, request = get[FacebookResults]),
    DhvConnector(baseId = 46445)
  )

  def startScheduler(): Unit = system.scheduler.schedule(1 second, 10 minutes) {
    val futUsers = get[List[User]](s"http://$crawlerHost:$crawlerPort/all-tags")
    val futureConnectors = Future.sequence(connectors.map(_.newResults))

    val finished = for {
      users <- futUsers
      conns <- futureConnectors
    } yield {
      conns.flatMap(_._1).toResult(users).foreach(ParaMail.sendMail(_, mail, pwd))
      connectors = conns.map(_._2)
    }
    finished.onFailure { case e => e.printStackTrace }
    finished.onSuccess { case _ => println("success") }

  }

  startScheduler()
}