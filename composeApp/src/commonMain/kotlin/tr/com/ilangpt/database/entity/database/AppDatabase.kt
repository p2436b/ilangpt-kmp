package tr.com.ilangpt.database.entity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tr.com.ilangpt.database.entity.Chat
import tr.com.ilangpt.database.entity.Listing

@Database(
  entities = [
    Chat::class,
    Listing::class,
  ],
  version = 1,
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun chatDao(): ChatDao
  abstract fun listingDao(): ListingDao
}