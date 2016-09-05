import akka.actor.ActorSystem
import scala.concurrent.duration._

/**
  * Created by 437580 on 05/09/16.
  */

case class User(email: String, lookingFor: List[String])
case class ParseResult(href: String, id: Int, title: String, imgSrc: String)
case class Result(user: User, parseResults: List[ParseResult])

object ParaParser extends App with Parsing {

  val system = ActorSystem()
  val users = List(User("yannick.gladow@gmail.com", List(
    "woody", "wood", "valley", "wani", "bright", "gradient"
  )))
  var currentId = 46445

  import system.dispatcher
  system.scheduler.schedule(1 second, 1 hour) {
    val (results, id) = giveMeNewResults(users)(currentId)
    results.foreach { res =>

      val msg =
        s"""
          |Hallo ${res.user.email}<br>
          |<br>
          |Es scheint wieder neue Angebote für deine Suche nach: ${res.user.lookingFor.mkString(", ")}<br>
          |zu geben. <br>
          |<br>
          |Check doch mal diese neuen Angebote<br>
          |<br>
          |${res.parseResults.map( result =>
            s"""Titel: ${result.title}<br>
               |Link: ${result.href}<br>
               |<br>
               |<img src="${result.imgSrc}" alt="img"/><br>
             """.stripMargin).mkString("<br> <br>")}
        """.stripMargin
      sendMail(msg, res.user.email)
    }
    currentId = id
  }


  def sendMail(msg: String, to: String): Unit = {
    import mail._
    send a new Mail(
      from = ("yannick.gladow@gmail.com", "Yannick"),
      to = to,
      subject = "Neues Angebot gefunden",
      message = "Hallo ",
      richMessage = Some(msg)
    )
  }
}
