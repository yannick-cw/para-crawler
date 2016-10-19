import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import parsing.{ParaParser, Scheduler}
import routes.RegistrationRoute

object Server extends App { self =>
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val conf: Config = system.settings.config

  val paraParser: ParaParser = Scheduler(
    self.conf.getString("facebook.token"),
    self.conf.getString("email.mail"),
    self.conf.getString("email.pwd")
  )

  val registrationRoute = new RegistrationRoute {
    override val paraParser: ParaParser = self.paraParser
  }
  Http().bindAndHandle(registrationRoute.route, "0.0.0.0", 8080)
  paraParser.startScheduler()
}
