package windymelt.dumpfav.repository

import windymelt.dumpfav.model.Cursor

trait CursorRepositoryComponent {
    val cursorRepository: CursorRepository

    trait CursorRepository {
        def getCursor(): Option[Cursor]
        def saveCursor(
            olderCursor: Long,
            laterCursor: Long,
            reachedBottom: Boolean
        ): Unit
    }
}