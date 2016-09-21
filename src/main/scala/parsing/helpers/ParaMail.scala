package parsing.helpers

import parsing.models.Result

/**
  * Created by Yannick on 18.09.16.
  */
object ParaMail {
  def sendMail(result: Result, mail: String, pwd: String): Unit = {
    Mail(
      from = mail,
      to = result.user.email,
      subject = "Neues Angebot gefunden",
      message = "Bitte aktiviere HTML support ",
      richMessage = result.renderedHtml
    ).send(mail, pwd)
  }
}
