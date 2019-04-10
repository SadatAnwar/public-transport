package service

import scala.concurrent.{ExecutionContext, Future}

import javax.inject.{Inject, Singleton}
import model.{Delay, Status}

@Singleton
class DelayService @Inject()(fileReaderService: FileReaderService, lineService: LineService)(implicit ec: ExecutionContext) {
  private val fileName = "data/delays.csv"

  def getStatus(line: String): Future[Status] = {
    lineService.loadLineByName(line).flatMap { l =>
      readFromCsv()
      .map(delays => delays.find(_.line.equals(l))
                     .map(d => Status(onTime = false, d.delayAmount))
                     .getOrElse(Status.onTime)
      )
    }
  }

  private def readFromCsv(): Future[List[Delay]] = {
    for {
      trainLines <- lineService.getAllLines()
      maybeDelay <- fileReaderService.readFile(fileName).map(
        _.drop(1).map(l => {
          val segments = l.split(",")
          trainLines.find(_.name.equals(segments(0))).map(Delay(_, segments(1).toInt))
        }))
    } yield {
      maybeDelay.flatten
    }
  }
}
