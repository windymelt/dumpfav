package windymelt.dumpfav

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{Tweet => OrigTweet, RatedData}
import upickle.default.write
import upickle.default.{ReadWriter => RW, macroRW}
import scala.concurrent.Future

case class Tweet(id: Long, text: String, user: String, createdAtEpochMillis: Long)
object Tweet {
  implicit val rw: RW[Tweet] = macroRW
}

case class Dump(cursor: Long, tweets: Seq[Tweet])
object Dump {
  implicit val rw: RW[Dump] = macroRW
}

trait TwitterComFavRepositoryComponent {

  val twitterComFavRepository: TwitterComFavRepository

  class TwitterComFavRepository {
    val client = TwitterRestClient()

    def findOlderFavs(screenName: String, cursor: Option[Long])(
        implicit ec: scala.concurrent.ExecutionContext
    ): Future[Dump] = {
      client
        .favoriteStatusesForUser(screenName, 200, None, cursor)
        .map(convert)
    }

    private implicit val convertTweet: OrigTweet => Tweet = (tw) => {
      Tweet(tw.id, tw.text, tw.user.map(_.screen_name).getOrElse("n/a"), tw.created_at.toEpochMilli())
    }

    private def convert(rd: RatedData[Seq[OrigTweet]]): Dump = {
      Dump(
        cursor = rd.data.minBy(_.id).id,
        tweets = rd.data.map(convertTweet)
      )
    }
  }
}
