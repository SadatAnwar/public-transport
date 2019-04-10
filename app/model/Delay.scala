package model

import play.api.libs.json.{Json, OFormat}

case class Delay(line: Line, delayAmount: Int)

object Delay {
  implicit val formatDelay: OFormat[Delay] = Json.format[Delay]
}



