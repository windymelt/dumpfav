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
    val future = twitterComFavRepository
      .findOlderFavs(screenName, cursorRepository.getCursor())
      .map(localFavRepository.commit)
      .andThen(_ => println("done"))
      .onComplete(_ => sys.exit(0))
  }
}
