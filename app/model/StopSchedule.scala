package model

import play.api.libs.json.{Json, OFormat}

case class StopSchedule(line: Option[Line], stop: Stop, time: String, status: Option[Status] = None)

object StopSchedule {
  implicit val formatTime: OFormat[StopSchedule] = Json.format[StopSchedule]
}
