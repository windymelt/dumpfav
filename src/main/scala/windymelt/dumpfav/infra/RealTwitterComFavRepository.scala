package windymelt.dumpfav.infra

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{Tweet => OrigTweet, RatedData}
import upickle.default.write
import upickle.default.{ReadWriter => RW, macroRW}
import scala.concurrent.Future
import windymelt.dumpfav.model.{Tweet, Dump}

trait RealTwitterComFavRepositoryComponent extends windymelt.dumpfav.repository.TwitterComFavRepositoryComponent {

  override val twitterComFavRepository: TwitterComFavRepository

  class RealTwitterComFavRepository extends TwitterComFavRepository {
    val client = TwitterRestClient()

    def findOlderFavs(screenName: String, cursor: Option[Long])(
        implicit ec: scala.concurrent.ExecutionContext
    ): Future[Dump] = {
      client
        .favoriteStatusesForUser(screenName, 200, None, cursor)
        .map(convert)
    }

    def findLaterFavs(screenName: String, cursor: Long)(
        implicit ec: scala.concurrent.ExecutionContext
    ): Future[Dump] = {
      client
        .favoriteStatusesForUser(screenName, 200, Some(cursor), None)
        .map(convert)
    }

    private implicit val convertTweet: OrigTweet => Tweet = (tw) => {
      Tweet(
        tw.id,
        tw.text,
        tw.user.map(_.screen_name).getOrElse("n/a"),
        tw.created_at.toEpochMilli()
      )
    }

    private def convert(rd: RatedData[Seq[OrigTweet]]): Dump = {
      Dump(
        olderCursor = rd.data.minBy(_.id).id,
        laterCursor = rd.data.maxBy(_.id).id,
        tweets = rd.data.map(convertTweet)
      )
    }
  }
}
