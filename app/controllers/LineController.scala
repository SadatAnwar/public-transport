package controllers

import scala.concurrent.ExecutionContext

import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import service.LineService

@Singleton
class LineController @Inject()(lineService: LineService)(implicit ec: ExecutionContext) extends InjectedController {

  def getAllLines() = Action.async { implicit request: Request[AnyContent] =>
    lineService.getAllLines().map(lines => Ok(Json.toJson(lines)))
  }
}
