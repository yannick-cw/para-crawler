import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import parsing.{ParaParser, Scheduler}
import routes.RegistrationRoute

object Server extends App { self =>
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val paraParser: ParaParser = new Scheduler {
    override val system: ActorSystem = self.system
  }

  val registrationRoute = new RegistrationRoute {
    override val paraParser: ParaParser = self.paraParser
  }
  Http().bindAndHandle(registrationRoute.route, "0.0.0.0", 8080)
  paraParser.startScheduler()
}
