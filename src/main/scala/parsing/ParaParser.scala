package parsing

import parsing.models.User

/**
  * Created by 437488 on 19/09/16.
  */
trait ParaParser {
  def updateUser(user: User): Unit
  def startScheduler(): Unit
}
