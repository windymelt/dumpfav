package windymelt.dumpfav

import io.Source
import java.io.File
import java.io.FileWriter
import upickle.default.{write, read}

trait CursorRepositoryComponent {

  val cursorRepository: CursorRepository

  class CursorRepository(configPath: String) {
    private val source = try {
      Source.fromFile(configPath)
    } catch {
      case e: java.io.FileNotFoundException =>
        val fc = new File(configPath).createNewFile()
        Source.fromFile(configPath)
    }

    def getCursor(): Option[Cursor] =
      try {
        Some(read[Cursor](new File(configPath)))
      } catch {
        case e: ujson.IncompleteParseException => None
      }

    def saveCursor(
        olderCursor: Long,
        laterCursor: Long,
        reachedBottom: Boolean
    ) = {
      val f = new File(configPath)
      val pwc = new FileWriter(f)
      val serialized = Cursor(olderCursor, laterCursor, reachedBottom)
      pwc.write(write(serialized))
      pwc.close()
    }
  }
}
