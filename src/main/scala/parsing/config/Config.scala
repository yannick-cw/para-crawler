package parsing.config

import com.typesafe.config.ConfigFactory

object Config {

  val conf = ConfigFactory.load()

//  val token = conf.getString("facebook.token")
  val mail  = conf.getString("email.mail")
  val pwd   = conf.getString("email.pwd")
//  val tagsApiHost = conf.getString("tagsApi.host")
//  val tagsApiPort = conf.getInt("tagsApi.port")
}
