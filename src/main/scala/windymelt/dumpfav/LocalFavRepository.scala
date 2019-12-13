package windymelt.dumpfav

import java.io.File
import java.io.FileWriter
import upickle.default.write
import upickle.default.{ReadWriter => RW, macroRW}

trait LocalFavRepositoryComponent {
  self: CursorRepositoryComponent =>

  val localFavRepository: LocalFavRepository

  class LocalFavRepository(dataFilePath: String) {
    def commit(d: Dump) = {
      cursorRepository.saveCursor(d.cursor - 1)
      val f = new File(dataFilePath)
      val fw = new FileWriter(f, true)
      d.tweets.foreach(tw => fw.append(write(tw) + "\n"))
      fw.close()
    }
  }
}
