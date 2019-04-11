package controllers

import scala.concurrent.ExecutionContext

import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import model.ScheduleRequest
import service.StopScheduleService

@Singleton
class ScheduleController @Inject()(stopScheduleService: StopScheduleService)(implicit ec: ExecutionContext) extends InjectedController {

  def getSchedule(scheduleRequest: ScheduleRequest) = Action.async { implicit request: Request[AnyContent] =>
    stopScheduleService.findVehicleAt(scheduleRequest.x, scheduleRequest.y, scheduleRequest.time)
    .map(lines => Ok(Json.toJson(lines)))
  }
}
