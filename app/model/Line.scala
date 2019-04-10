package model

import play.api.libs.json.Json

case class Line(id: Long, name: String)

object Line {
  implicit val format = Json.format[Line]
}
