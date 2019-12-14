package windymelt.dumpfav

import java.io.File
import java.io.FileWriter
import upickle.default.write
import upickle.default.{ReadWriter => RW, macroRW}

trait LocalFavRepositoryComponent {
  self: CursorRepositoryComponent =>

  val localFavRepository: LocalFavRepository

  class LocalFavRepository(dataFilePath: String) {
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
