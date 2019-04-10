package exception

case class LineNotFoundException(searchedFor: String) extends RuntimeException(s"$searchedFor is not present in lines.csv")
