package exception

case class StopNotFoundException(searchedFor: String) extends NotFoundError(s"$searchedFor is not present in stops.csv")
