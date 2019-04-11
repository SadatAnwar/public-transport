package service

import scala.concurrent.{ExecutionContext, Future}

import javax.inject.{Inject, Singleton}
import model.StopSchedule

@Singleton
class StopScheduleService @Inject()(fileReaderService: FileReaderService, lineService: LineService, stopsService: StopsService)(implicit ec: ExecutionContext) {

  private val fileName = "data/times.csv"

  def findVehicleAt(x: Double, y: Double, time: String): Future[Option[StopSchedule]] = {
    stopsService.loadStopByCoordinates(x, y).flatMap(stop => {
      readFromCsv().map(stopSchedules => stopSchedules.find(sc => sc.stop.equals(stop) && sc.time.equals(time)))
    })
  }

  private def readFromCsv(): Future[List[StopSchedule]] = {
    fileReaderService.readFile(fileName)
    .flatMap(lines => {
      val futureLines: List[Future[StopSchedule]] = lines.drop(1).map(line => {
        val segments = line.split(",")
        val lineId = segments(0).toLong
        val stopId = segments(1).toLong
        val time = segments(2)

        lineService.loadLineById(lineId).flatMap(l => {
          stopsService.loadStopById(stopId).map(s => StopSchedule(l, s, time))
        })
      })
      Future.sequence(futureLines)
    })
  }
}
