package service

import scala.concurrent.Future
import scala.io.Source

import configuration.IOExecutionContext
import javax.inject.{Inject, Singleton}

@Singleton
class FileReaderService @Inject()(implicit executionContext: IOExecutionContext) {

  def readFile(fileName: String): Future[List[String]] = {
    Future.apply(Source.fromFile(fileName).getLines().toList)
  }
}
