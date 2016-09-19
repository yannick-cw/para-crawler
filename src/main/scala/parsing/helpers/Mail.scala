package parsing.helpers

case class Mail(from: String,
                to: String,
                subject: String,
                message: String,
                richMessage: String) {
  def send: String = {
    import org.apache.commons.mail._

    val commonsMail: Email = new HtmlEmail().setHtmlMsg(richMessage).setTextMsg(message)

    commonsMail.addTo(to)

    val email = commonsMail
      .setFrom(from)
      .setSubject(subject)
    email.setHostName("smtp.googlemail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator(sys.env("EMAIL"), sys.env("EMAIL_PWD")))
    email.setSSLOnConnect(true)
    email.send
  }
}
