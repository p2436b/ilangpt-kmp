package tr.com.ilangpt.database.entity.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import tr.com.ilangpt.database.entity.Chat

@Dao
interface ChatDao {
  @Insert
  suspend fun insert(chat: Chat): Long

  @Delete
  suspend fun delete(chat: Chat)

  @Query("SELECT * FROM chats")
  suspend fun getAll(): List<Chat>
}