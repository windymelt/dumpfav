package windymelt.dumpfav.infra

import java.io.File
import java.io.FileWriter
import upickle.default.write
import upickle.default.{ReadWriter => RW, macroRW}
import windymelt.dumpfav.model.{Cursor, Dump}

trait LocalFileFavRepositoryComponent extends windymelt.dumpfav.repository.FavTankRepositoryComponent {
  self: CursorRepositoryComponent =>

  override val favTankRepository: FavTankRepository

  class LocalFileFavRepository(dataFilePath: String) extends FavTankRepository {
    def commit(preCursor: Option[Cursor])(d: Dump) = {
      cursorRepository.saveCursor(
        olderCursor = preCursor
          .map(c => math.min(d.olderCursor - 1, c.olderCursor))
          .getOrElse(d.olderCursor - 1),
        laterCursor = preCursor
          .map(c => math.max(d.laterCursor, c.laterCursor))
          .getOrElse(d.laterCursor),
        reachedBottom = d.tweets.size == 0
      )
      val f = new File(dataFilePath)
      val fw = new FileWriter(f, true)
      d.tweets.foreach(tw => fw.append(write(tw) + "\n"))
      fw.close()
    }
  }
}
