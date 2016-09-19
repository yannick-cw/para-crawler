package routes

import akka.http.scaladsl.model.{ContentType, ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpecLike}
import parsing.ParaParser
import parsing.helpers.Protocols
import parsing.models.User
import spray.json._

class RegistrationRouteSpec extends WordSpecLike with Matchers with ScalatestRouteTest with Protocols {
  val regRoute = new RegistrationRoute {
    override val paraParser: ParaParser = new ParaParser {
      override def updateUser(user: User): Unit = ()
      override def startScheduler(): Unit = ()
    }
  }
    "Registration route" should {
    val validUser = User("test@test.de", List("woopwoop")).toJson.compactPrint
    "Return 201 on success" in {
      Put("/register", HttpEntity(ContentTypes.`application/json`, validUser)) ~> regRoute.route ~> check {
        status should be (StatusCodes.Created)
      }
    }
  }
}
