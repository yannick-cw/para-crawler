package parsing.helpers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import parsing.models.User
import spray.json.DefaultJsonProtocol

trait Protocols extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val userFormat = jsonFormat2(User)
}
