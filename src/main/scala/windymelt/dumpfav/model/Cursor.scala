package windymelt.dumpfav.model

import upickle.default.{ReadWriter => RW, macroRW}

case class Cursor(
    olderCursor: Long,
    laterCursor: Long,
    reachedBottom: Boolean
)
object Cursor {
  implicit val rw: RW[Cursor] = macroRW
}