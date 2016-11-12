package parsing.helpers

case class Mail(from: String,
                to: String,
                subject: String,
                message: String,
                richMessage: String) {
  def send(mail: String, pwd: String): String = {
    import org.apache.commons.mail._

    val commonsMail: Email = new HtmlEmail().setHtmlMsg(richMessage).setTextMsg(message)

    commonsMail.addTo(to)

    val email = commonsMail
      .setFrom(from)
      .setSubject(subject)
    email.setHostName("smtp.googlemail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator(mail, pwd))
    email.setSSLOnConnect(true)
    email.send
  }
}
