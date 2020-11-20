package parsing

import akka.actor.ActorSystem
import akka.stream.Materializer
import parsing.config.Config._
import parsing.helpers.ParseResultsOps.ParseResultsImproved
import parsing.helpers.{ParaMail, Requester}
import parsing.models.User
import parsing.web_connectors.{Connector, DhvConnector}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Scheduler extends App with Requester {

  implicit val system                     = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)

  var connectors: List[Connector] = List(
//    FacebookConnector(0, accessToken = token, request = get[FacebookResults]),
    DhvConnector(baseId = 46445)
  )

  def startScheduler(): Unit = system.scheduler.schedule(1.second, 10.minutes) {
    val futUsers         = Future.successful(List.empty[User])
    val futureConnectors = Future.sequence(connectors.map(_.newResults))

    val finished = for {
      users <- futUsers
      conns <- futureConnectors
    } yield {
      conns.flatMap(_._1).toResult(users).foreach(ParaMail.sendMail(_, mail, pwd))
      connectors = conns.map(_._2)
    }
    finished.onComplete {
      case Failure(e) => e.printStackTrace()
      case Success(_) => println("done")
    }
  }

  startScheduler()
}
