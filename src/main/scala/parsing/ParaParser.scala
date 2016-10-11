package parsing

import parsing.models.User

trait ParaParser {
  def updateUser(user: User): Unit
  def startScheduler(): Unit
}
