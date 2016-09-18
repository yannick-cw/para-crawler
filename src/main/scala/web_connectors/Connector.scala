package web_connectors

import models.ParseResult

/**
  * Created by Yannick on 18.09.16.
  */
trait Connector {
  def newResults: (List[ParseResult], Connector)
}
