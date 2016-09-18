import akka.actor.ActorSystem
import helpers.ParaMail
import helpers.ParseResultsOps.ParseResultsImproved
import models.User
import web_connectors.{Connector, DhvConnector}

import scala.concurrent.duration._

object ParaParser extends App {

  val system = ActorSystem()
  val users = List(User("yannick.gladow@gmail.com", List("woody", "wood", "valley", "wani", "bright", "denali")),
    User("antonia.kahlert@gmail.com", List("woody", "wood", "valley", "wani", "prion", "koyote", "koyot")))

  var connectors: List[Connector] = List(DhvConnector(baseId = 46445))
  import system.dispatcher

  system.scheduler.schedule(1 second, 1 hour) {
    connectors = connectors.map { connector =>
      val (parseResults, newConnector) = connector.newResults
      parseResults.toResult(users)
        .foreach(ParaMail.sendMail)
      newConnector
    }
  }
}
