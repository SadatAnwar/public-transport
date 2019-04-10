package service

import scala.concurrent.{ExecutionContext, Future}

import javax.inject.{Inject, Singleton}
import model.Stop

@Singleton
class StopsService @Inject()(fileReaderService: FileReaderService)(implicit ec: ExecutionContext) {

  private val fileName = "data/stops.csv"

  def getAllStops(): Future[List[Stop]] = {
    readFromCsv()
  }

  private def readFromCsv(): Future[List[Stop]] = {
    fileReaderService.readFile(fileName)
    .map(_.drop(1)
          .map(line => {
            val segments = line.split(",")
            Stop(segments(0).toLong, segments(1).toDouble, segments(2).toDouble)
          }))
  }
}
