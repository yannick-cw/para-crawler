package parsing.models

import org.joda.time.DateTime

case class FacebookResult(created_time: String, message: String, permalink_url: String, picture: String) {
  def toParseResult: ParseResult = ParseResult(permalink_url, new DateTime(created_time).getMillis, message, picture)
}

case class FacebookResults(data: List[FacebookResult]) {
  def toParseResults: List[ParseResult] = data.map(_.toParseResult)
}
