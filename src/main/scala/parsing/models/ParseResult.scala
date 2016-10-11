package parsing.models

/**
  * Created by Yannick on 18.09.16.
  */
case class ParseResult(href: String, id: Long, title: String, imgSrc: String) {
  def contains(lookingFor: List[String]) = lookingFor.exists(title.toLowerCase.contains)
  def notContains(filterWords: List[String]) = !contains(filterWords)
}
