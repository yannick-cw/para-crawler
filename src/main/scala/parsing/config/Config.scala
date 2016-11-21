package parsing.config

import com.typesafe.config.{Config, ConfigFactory}

object Config {

  val conf: Config = ConfigFactory.load()

  val token = conf.getString("facebook.token")
  val mail = conf.getString("email.mail")
  val pwd = conf.getString("email.pwd")
  val crawlerHost = conf.getString("crawler.host")
  val crawlerPort = conf.getInt("crawler.port")
  val unsubUrl = conf.getString("email.unsubUrl")
}
