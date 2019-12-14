package windymelt.dumpfav

import scala.language.postfixOps
import scala.concurrent.duration._
import scala.concurrent.Await

trait DumpFav {
  self: CursorRepositoryComponent
    with LocalFavRepositoryComponent
    with TwitterComFavRepositoryComponent =>

  def run(screenName: String) = {
    implicit val ec: scala.concurrent.ExecutionContext =
      scala.concurrent.ExecutionContext.global
      val cursor = cursorRepository.getCursor()
    val future = twitterComFavRepository
      .findOlderFavs(screenName, cursor.map(_.olderCursor))
      .map(localFavRepository.commit(preCursor = cursor))
      .andThen(_ => println("done"))
      .onComplete(_ => sys.exit(0))
  }
}
