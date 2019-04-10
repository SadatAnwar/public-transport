package service

import scala.concurrent.{ExecutionContext, Future}

import exception.LineNotFoundException
import javax.inject.{Inject, Singleton}
import model.Line

@Singleton
class LineService @Inject()(fileReaderService: FileReaderService)(implicit ec: ExecutionContext) {

  private val fileName = "data/lines.csv"

  def loadLineByName(name: String): Future[Line] = {
    findLineByName(name) map {
      case Some(l) => l
      case None    => throw LineNotFoundException(name)
    }
  }

  private def findLineByName(name: String): Future[Option[Line]] = {
    getAllLines().map { lines =>
      lines.find(_.name.equals(name))
    }
  }

  def findLineById(id: Long): Future[Option[Line]] = {
    getAllLines().map { lines =>
      lines.find(_.id.equals(id))
    }
  }

  def getAllLines(): Future[List[Line]] = {
    readFromCsv()
  }

  private def readFromCsv(): Future[List[Line]] = {
    fileReaderService.readFile(fileName)
    .map(_.drop(1)
          .map(line => {
            val segments = line.split(",")
            Line(segments(0).toLong, segments(1))
          }))
  }
}
