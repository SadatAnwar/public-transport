package exception

case class LineNotFoundException(searchedFor: String) extends NotFoundError(s"$searchedFor is not present in lines.csv")
