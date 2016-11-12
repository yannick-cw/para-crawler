package parsing.helpers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import spray.json.RootJsonFormat

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Requester extends SprayJsonSupport {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  def get[T](uri: Uri)(implicit format: RootJsonFormat[T]): Future[T] =
    Http().singleRequest(HttpRequest(uri = uri))
      .flatMap(resp => Unmarshal(resp.entity.withContentType(ContentTypes.`application/json`)).to[T])
}
