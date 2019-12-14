package windymelt.dumpfav.model

import upickle.default.{ReadWriter => RW, macroRW}

case class Tweet(
    id: Long,
    text: String,
    user: String,
    createdAtEpochMillis: Long
)
object Tweet {
  implicit val rw: RW[Tweet] = macroRW
}