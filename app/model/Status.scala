package model

import play.api.libs.json.{Json, OFormat}

case class Status(onTime: Boolean, amount: Int)

object Status {
  implicit val formatStatus: OFormat[Status] = Json.format[Status]
  val onTime = Status(onTime = true, 0)
}

