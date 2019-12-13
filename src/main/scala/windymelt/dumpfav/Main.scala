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
    with CursorRepositoryComponent
    with LocalFavRepositoryComponent
    with TwitterComFavRepositoryComponent {
  override val cursorRepository = new CursorRepository("tmp/tweetdump.conf")
  override val localFavRepository = new LocalFavRepository("tmp/tweetdump.dat")
  override val twitterComFavRepository = new TwitterComFavRepository()
  this.run()
}
