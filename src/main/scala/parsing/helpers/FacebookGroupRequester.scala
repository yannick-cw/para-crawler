package parsing.helpers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import parsing.models.FacebookResults

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait FacebookGroupRequester extends Protocols {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  def request(uri: Uri): Future[FacebookResults] =
    Http().singleRequest(HttpRequest(uri = uri))
      .flatMap(resp => Unmarshal(resp.entity.withContentType(ContentTypes.`application/json`)).to[FacebookResults])

}
