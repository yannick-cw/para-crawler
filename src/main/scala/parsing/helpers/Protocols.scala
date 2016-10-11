package parsing.helpers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import parsing.models.{FacebookResult, FacebookResults, User}
import spray.json.DefaultJsonProtocol

trait Protocols extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val userFormat = jsonFormat2(User)
  implicit val facebookResultFormat = jsonFormat4(FacebookResult)
  implicit val facebookResultsFormat = jsonFormat1(FacebookResults)
}
