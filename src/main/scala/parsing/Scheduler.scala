package parsing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import parsing.helpers.ParseResultsOps.ParseResultsImproved
import parsing.helpers.{FacebookGroupRequester, ParaMail}
import parsing.models.User
import parsing.web_connectors.{Connector, DhvConnector, FacebookConnector}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

class Scheduler(token: String, mail: String, pwd: String)
  (implicit val system: ActorSystem, val materializer: ActorMaterializer)
  extends ParaParser with FacebookGroupRequester {

  var users: Map[String, User] = Map.empty

  var connectors: List[Connector] = List(
    DhvConnector(baseId = 46445),
    FacebookConnector(0, accessToken = token, request = request)
  )

  def updateUser(user: User): Unit =
    users = users.updated(user.email, user)

  def startScheduler(): Unit = system.scheduler.schedule(10 second, 10 minutes) {
    Future.sequence(connectors.map { connector =>
      connector.newResults.map { case (parseResults, newConnector) =>
        parseResults.toResult(users.values.toList)
          .foreach(ParaMail.sendMail(_, mail, pwd))
        newConnector
      }
    }).foreach(nc => connectors = nc)
  }
}

object Scheduler {
  def apply(token: String, mail: String, pwd: String)
    (implicit system: ActorSystem, materializer: ActorMaterializer): Scheduler =
    new Scheduler(token, mail, pwd)
}