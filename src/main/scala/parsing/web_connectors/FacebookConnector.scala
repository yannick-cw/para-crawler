package parsing.web_connectors

import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Query
import parsing.models.{FacebookResults, ParseResult}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.math.max

case class FacebookConnector(baseId: Long,
  groupId: String = "129251540443443",
  accessToken: String,
  request: (Uri) => Future[FacebookResults]
) extends Connector {

  override def newResults: Future[(List[ParseResult], Connector)] = {
    val query = Query(Map(
      "access_token" -> accessToken,
      "limit" -> "100",
      "fields" -> "created_time,message,permalink_url,picture"
    ))
    val uri = Uri(s"https://graph.facebook.com/$groupId/feed").withQuery(query)

    request(uri)
      .map(_.toParseResults)
      .map(_.filter(_.id > baseId))
      .map(results => (results, this.copy(results.map(_.id).foldLeft(baseId)(max))))
  }
}
