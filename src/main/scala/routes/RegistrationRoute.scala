package routes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import parsing.ParaParser
import parsing.helpers.Protocols
import parsing.models.User

trait RegistrationRoute extends Protocols {
  val paraParser: ParaParser

  val route: Route = logRequestResult("reqRes") {
    path("register") {
      put {
        entity(as[User]) { user =>
          paraParser.updateUser(user)
          complete(Created -> "User successfully registered")
        }
      }
    }
  }
}
