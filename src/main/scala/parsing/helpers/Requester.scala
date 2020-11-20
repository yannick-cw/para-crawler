package parsing.helpers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Decoder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Requester extends FailFastCirceSupport {
  implicit val system: ActorSystem
  implicit val materializer: Materializer

  def get[T](uri: Uri)(implicit format: Decoder[T]): Future[T] =
    Http().singleRequest(HttpRequest(uri = uri))
      .flatMap(resp => Unmarshal(resp.entity.withContentType(ContentTypes.`application/json`)).to[T])
}
