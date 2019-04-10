package model

import play.api.libs.json.{Json, OFormat}

case class ServerError(message: String, reference: String)

object ServerError {
  implicit val formatServerError: OFormat[ServerError] = Json.format[ServerError]
}
