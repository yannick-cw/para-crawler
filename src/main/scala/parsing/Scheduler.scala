package parsing

import akka.actor.ActorSystem
import helpers.ParaMail
import helpers.ParseResultsOps.ParseResultsImproved
import models.User
import web_connectors.{Connector, DhvConnector}

import scala.concurrent.duration._

trait Scheduler extends ParaParser {
  val system: ActorSystem
  var users = Map.empty[String, User]
  lazy val conf = system.settings.config

  var connectors: List[Connector] = List(DhvConnector(baseId = 46445))
  import system.dispatcher

  def updateUser(user: User): Unit =
    users = users.updated(user.email, user)

  def startScheduler(): Unit = system.scheduler.schedule(10 second, 10 minutes) {
    connectors = connectors.map { connector =>
      val (parseResults, newConnector) = connector.newResults
      parseResults.toResult(users.values.toList)
        .foreach(ParaMail.sendMail(_, conf.getString("email.mail"), conf.getString("email.pwd")))
      newConnector
    }
  }
}
