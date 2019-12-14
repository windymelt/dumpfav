package windymelt.dumpfav.repository

import windymelt.dumpfav.model.{Cursor, Dump}

trait FavTankRepositoryComponent {
    self: CursorRepositoryComponent =>

    val favTankRepository: FavTankRepository

    trait FavTankRepository {
        def commit(preCursor: Option[Cursor])(d: Dump): Unit
    }
}