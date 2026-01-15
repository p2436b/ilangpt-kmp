package tr.com.ilangpt.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class Chat(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val title: String
)
