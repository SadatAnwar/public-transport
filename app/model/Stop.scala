package model

import play.api.libs.json.{Json, OFormat}

case class Stop(id: Long, x: Double, y: Double)

object Stop {
  implicit val formatStop: OFormat[Stop] = Json.format[Stop]
}


