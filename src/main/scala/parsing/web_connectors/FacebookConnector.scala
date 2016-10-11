package parsing.web_connectors
import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.headers.Date
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import org.joda.time.DateTime
import parsing.helpers.{HttpRequester, Protocols}
import parsing.models.{FacebookResults, ParseResult}
import scala.math.max

import scala.concurrent.Future

case class FacebookConnector(baseId: Long,
                             groupId: String = "129251540443443",
                             accessToken: String) extends Connector with HttpRequester with Protocols {

  override implicit val system: ActorSystem = ActorSystem()
  override implicit val materializer: ActorMaterializer = ActorMaterializer()

  override def newResults: Future[(List[ParseResult], Connector)] = {
    val query = Query(Map(
      "access_token" -> accessToken,
      "limit" -> "1000",
      "fields" -> "created_time,message,permalink_url,picture"
    ))
    val uri = Uri(s"https://graph.facebook.com/$groupId/feed").withQuery(query)
    request(uri)
      .flatMap(resp => Unmarshal(resp.entity).to[FacebookResults])
      .map(_.toParseResults)
      .map(_.filter(_.id > baseId))
      .map(results => (results, this.copy(results.map(_.id).foldLeft(baseId)(max))))
  }
}
