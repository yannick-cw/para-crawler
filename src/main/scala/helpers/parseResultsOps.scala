package helpers

import models.{ParseResult, Result, User}

/**
  * Created by Yannick on 18.09.16.
  */
object ParseResultsOps {

  implicit class ParseResultsImproved(parseResults: List[ParseResult]) {
    def toResult(users: List[User]): List[Result] =
      users
        .map { user => Result(user,
          parseResults
            .filter(_.contains(user.lookingFor))
            .filter(_.notContains(List("suche"))))
        }
        .filter(_.parseResults.nonEmpty)
  }

}
