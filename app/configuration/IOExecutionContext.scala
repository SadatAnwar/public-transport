package configuration

import scala.concurrent.ExecutionContext

import play.api.libs.concurrent.CustomExecutionContext

import akka.actor.ActorSystem
import com.google.inject.Inject
import javax.inject.Singleton

@Singleton
class IOExecutionContext @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "IO-execution-context") with ExecutionContext
