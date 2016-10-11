package parsing.web_connectors

import parsing.models.ParseResult

import scala.concurrent.Future

trait Connector {
  def newResults: Future[(List[ParseResult], Connector)]
}
