package controllers

import scala.concurrent.ExecutionContext

import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import service.DelayService

@Singleton
class DelayController @Inject()(delayService: DelayService)(implicit ec: ExecutionContext) extends InjectedController {

  def getStatus(line: String) = Action.async { implicit request: Request[AnyContent] =>
    delayService.getStatus(line)
    .map(lines => Ok(Json.toJson(lines)))
  }
}
