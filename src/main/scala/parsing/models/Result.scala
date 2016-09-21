package parsing.models

/**
  * Created by Yannick on 18.09.16.
  */
case class Result(user: User, parseResults: List[ParseResult]) {
  val renderedHtml =
    s"""
       |Hallo ${user.email}<br>
       |<br>
       |Es scheint wieder neue Angebote für deine Suche nach: ${user.lookingFor.mkString(", ")}<br>
       |zu geben. <br>
       |<br>
       |Check doch mal diese neuen Angebote<br>
       |<br>
       |${ parseResults.map(result =>
        s"""Titel: ${result.title}<br>
            |Link: ${result.href}<br>
            |<br>
            |<img src="${result.imgSrc}" alt="img"/><br>
             """.stripMargin).mkString("<br> <br>") }
        """.stripMargin
}