package controllers

import scala.concurrent.Future

import play.api.http.DefaultHttpErrorHandler
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{RequestHeader, Result, Results}
import play.api.routing.Router
import play.api.{Configuration, Environment, OptionalSourceMapper}

import exception.NotFoundError
import javax.inject.{Inject, Provider, Singleton}
import model.ServerError

@Singleton
class ErrorController @Inject()(env: Environment,
                                config: Configuration,
                                sourceMapper: OptionalSourceMapper,
                                router: Provider[Router],
                               ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    val error = exception match {
      case e: NotFoundError => Results.NotFound(toJsonErrorMessage(e.getMessage, e.getClass.getName))

      case e: Throwable => Results.InternalServerError(toJsonErrorMessage(s"Internal error $e", e.getClass.getName))
    }

    Future.successful(error)
  }

  private def toJsonErrorMessage(message: String, reference: String): JsValue = {
    Json.toJson(ServerError(message, reference))
  }
}
