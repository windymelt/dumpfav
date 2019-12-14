package windymelt.dumpfav
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{Tweet => OrigTweet, RatedData}
import upickle.default.write
import upickle.default.{ReadWriter => RW, macroRW}
import java.io.Reader
import java.io.FileReader

object DumpFavApp
    extends App
    with DumpFav
    with infra.CursorRepositoryComponent
    with infra.LocalFileFavRepositoryComponent
    with infra.RealTwitterComFavRepositoryComponent {
  override val cursorRepository = new LocalCursorRepository("tmp/tweetdump.conf")
  override val favTankRepository = new LocalFileFavRepository("tmp/tweetdump.dat")
  override val twitterComFavRepository = new RealTwitterComFavRepository()
  this.run("windymelt")
}
