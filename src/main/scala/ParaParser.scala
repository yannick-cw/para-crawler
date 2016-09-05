import akka.actor.ActorSystem
import scala.concurrent.duration._

/**
  * Created by 437580 on 05/09/16.
  */

case class User(email: String, lookingFor: List[String])
case class ParseResult(href: String, id: Int, title: String)
case class Result(user: User, parseResults: List[ParseResult])

object ParaParser extends App with Parsing {

  val system = ActorSystem()
  val users = List(User("yannick.gladow@gmail.com", List(
    "woody", "wood", "valley", "wani", "bright", "gradient"
  )))
  var currentId = 0

  import system.dispatcher
  system.scheduler.schedule(1 second, 1 hour) {
    val (results, id) = giveMeNewResults(users)(currentId)
    results.foreach { res =>

      val msg =
        s"""
          |Hallo ${res.user.email}
          |
          |Es scheint wieder neue Angebote für deine Suche nach: ${res.user.lookingFor.mkString(", ")}
          |zu geben.
          |
          |Check doch mal diese neuen Angebote
          |
          |${res.parseResults.map( result =>
            s"""Titel: ${result.title}
               |Link: ${result.href}
             """.stripMargin).mkString("\n \n")}
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
      message = msg
    )
  }
}
