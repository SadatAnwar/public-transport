package model

import play.api.libs.json.{Json, OFormat}

case class StopSchedule(line: Line, stop: Stop, time: String)

object StopSchedule {
  implicit val formatTime: OFormat[StopSchedule] = Json.format[StopSchedule]
}
