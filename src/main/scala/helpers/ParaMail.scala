package helpers

import models.Result

/**
  * Created by Yannick on 18.09.16.
  */
object ParaMail {
  def sendMail(result: Result): Unit = {
    Mail(
      from = sys.env("EMAIL"),
      to = result.user.email,
      subject = "Neues Angebot gefunden",
      message = "Bitte aktiviere HTML support ",
      richMessage = result.renderedHtml
    ).send
  }
}
