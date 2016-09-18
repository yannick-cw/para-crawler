package web_connectors

import models.ParseResult
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

/**
  * Created by 437580 on 05/09/16.
  */
case class DhvConnector(baseId: Int) extends Connector {

  val browser = JsoupBrowser()

  def newResults: (List[ParseResult], DhvConnector) = {
    val doc = browser.get("http://www.dhv.de/db3/gebrauchtmarkt/anzeigen?suchbegriff=&rubrik=0&hersteller=&muster=&preismin=&preismax=&anbietertyp=0&land=0&plz=&itemsperpage=1000&order=1")
    val items: List[ParseResult] =
      (doc >> elementList(".gm_offer"))
        .map { ele =>
          val subEle = ele >> element(".gm_offer_description")
          val image = ele >> element(".gm_offer_image")
          val imgSrc = image.children.head.children.head.attr("src")
          val details = subEle >> element(".dtl")
          val href = details.attr("href")
          val title = subEle >> text("h2")
          ParseResult(
            href = "http://www.dhv.de" + href,
            id = href.split("/").last.toInt,
            title = title,
            imgSrc = "http://www.dhv.de" + imgSrc
          )
        }
        .filter(_.id > baseId)

    (items.distinct, DhvConnector(items.headOption.map(_.id).getOrElse(baseId)))
  }
}
