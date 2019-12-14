package windymelt.dumpfav.model

import upickle.default.{ReadWriter => RW, macroRW}

case class Dump(olderCursor: Long, laterCursor: Long, tweets: Seq[Tweet])
object Dump {
  implicit val rw: RW[Dump] = macroRW
}