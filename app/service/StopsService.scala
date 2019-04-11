package service

import scala.concurrent.{ExecutionContext, Future}

import exception.StopNotFoundException
import javax.inject.{Inject, Singleton}
import model.Stop

@Singleton
class StopsService @Inject()(fileReaderService: FileReaderService)(implicit ec: ExecutionContext) {

  private val fileName = "data/stops.csv"

  def loadStopById(id: Long): Future[Stop] = {
    findLineById(id) map {
      case Some(l) => l
      case None    => throw StopNotFoundException(s"ID[$id]")
    }
  }

  def loadStopByCoordinates(x: Double, y: Double): Future[Stop] = {
    findLineByCoordinates(x, y) map {
      case Some(l) => l
      case None    => throw StopNotFoundException(s"Coordinates:[x: $x, y: $y]")
    }
  }

  private def findLineById(id: Long): Future[Option[Stop]] = {
    readFromCsv().map { stops =>
      stops.find(_.id.equals(id))
    }
  }

  private def findLineByCoordinates(x: Double, y: Double): Future[Option[Stop]] = {
    readFromCsv().map { stops =>
      stops.find(s => s.x.equals(x) && s.y.equals(y))
    }
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
