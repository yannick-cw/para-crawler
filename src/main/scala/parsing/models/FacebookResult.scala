package parsing.models

import org.joda.time.DateTime

case class FacebookResult(created_time: Option[String], message: Option[String], permalink_url: Option[String], picture: Option[String]) {
  def toParseResult: Option[ParseResult] = for {
    link <- permalink_url
    created <- created_time
    m <- message
    p <- picture
  } yield ParseResult(link, new DateTime(created).getMillis, m, p)
}

case class FacebookResults(data: List[FacebookResult]) {
  def toParseResults: List[ParseResult] = data.flatMap(_.toParseResult)
}
