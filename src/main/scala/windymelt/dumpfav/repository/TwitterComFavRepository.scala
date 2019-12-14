package windymelt.dumpfav.repository

import scala.concurrent.Future
import windymelt.dumpfav.model.Dump

trait TwitterComFavRepositoryComponent {
  val twitterComFavRepository: TwitterComFavRepository
  
  trait TwitterComFavRepository {
    
    def findOlderFavs(screenName: String, cursor: Option[Long])(
        implicit ec: scala.concurrent.ExecutionContext
    ): Future[Dump]

    def findLaterFavs(screenName: String, cursor: Long)(
        implicit ec: scala.concurrent.ExecutionContext
    ): Future[Dump]
  }
}
