package parsing.web_connectors

import parsing.models.ParseResult
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import scala.math.max

case class DhvConnector(baseId: Long) extends Connector {

  val browser = JsoupBrowser()

  def newResults: Future[(List[ParseResult], Connector)] = {
    val futureDoc = Future(
      browser.get(
        "http://www.dhv.de/db3/gebrauchtmarkt/anzeigen?suchbegriff=&rubrik=0&hersteller=&muster=&preismin=&preismax=&anbietertyp=0&land=0&plz=&itemsperpage=1000&order=1"
      )
    )
    futureDoc.map { doc =>
      val items: List[ParseResult] =
        (doc >> elementList(".gm_offer"))
          .map { ele =>
            val subEle  = ele >> element(".gm_offer_description")
            val image   = ele >> element(".gm_offer_image")
            val imgSrc  = image.children.head.children.head.attr("src")
            val details = subEle >> element(".dtl")
            val href    = details.attr("href")
            val title   = subEle >> text("h2")
            ParseResult(
              href = "http://www.dhv.de" + href,
              id = href.split("/").last.toInt,
              title = title,
              imgSrc = Some(imgSrc)
            )
          }
          .filter(_.id > baseId)

      (items, DhvConnector(items.map(_.id).foldLeft(baseId)(max)))
    }
  }
}
