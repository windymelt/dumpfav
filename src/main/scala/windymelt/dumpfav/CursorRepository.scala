package windymelt.dumpfav

import io.Source
import java.io.File
import java.io.FileWriter

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

    def getCursor(): Option[Long] = {
      val cursor = source.getLines().nextOption()
      return cursor.flatMap(_.toLongOption)
    }

    def saveCursor(cursor: Long) = {
      val f = new File(configPath)
      val pwc = new FileWriter(f)
      pwc.write(cursor.toString())
      pwc.close()
    }
  }
}
