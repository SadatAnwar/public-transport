package model

case class ScheduleRequest(x: Double, y: Double, time: String)

import play.api.mvc.QueryStringBindable

object ScheduleRequest {
  implicit def queryStringBindable(implicit
                                   stringBinder: QueryStringBindable[String],
                                   doubleBinder: QueryStringBindable[Double],
                                  ): QueryStringBindable[ScheduleRequest] {
    def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, ScheduleRequest]]

    def unbind(key: String, scheduleRequest: ScheduleRequest): String
  } = new QueryStringBindable[ScheduleRequest] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, ScheduleRequest]] = {
      for {
        x <- doubleBinder.bind("x", params)
        y <- doubleBinder.bind("y", params)
        time <- stringBinder.bind("time", params)
      } yield {
        (x, y, time) match {
          case (Right(x), Right(y), Right(time)) => {
            Right(ScheduleRequest(x, y, time))
          }
          case _                                 => {
            Left("Error binding suggestion query parameters")
          }
        }
      }
    }

    override def unbind(key: String, scheduleRequest: ScheduleRequest): String = {
      Seq(
        doubleBinder.unbind("q", scheduleRequest.x),
        doubleBinder.unbind("rows", scheduleRequest.y),
        stringBinder.unbind("fuzzy", scheduleRequest.time),
      ).mkString("&")
    }
  }
}
