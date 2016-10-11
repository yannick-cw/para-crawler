package parsing.helpers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.ActorMaterializer

import scala.concurrent.Future

trait HttpRequester {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  def request(uri: Uri): Future[HttpResponse] =
    Http().singleRequest(HttpRequest(uri = uri))
}
