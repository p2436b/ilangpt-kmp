package tr.com.ilangpt.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings")
data class Listing(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val title: String,
  val url: String,
  val coverImage: String,
  val websiteName: String,
  val city: String,
  val district: String,
  val neighborhood: String,
  val listingType: String,
  val categoryPath: String,
  val chatId: Int
)