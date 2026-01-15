package tr.com.ilangpt.database.entity.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import tr.com.ilangpt.database.entity.Listing

@Dao
interface ListingDao {
  @Insert
  suspend fun insert(listing: Listing): Long

  @Delete
  suspend fun delete(listing: Listing)

  @Query("SELECT * FROM listings")
  suspend fun getAll(): List<Listing>
}