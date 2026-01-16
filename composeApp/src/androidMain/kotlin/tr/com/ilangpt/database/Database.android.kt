package tr.com.ilangpt.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import tr.com.ilangpt.database.database.AppDatabase

//fun getAppDatabase(context: Context): AppDatabase {
//  val dbFile = context.getDatabasePath("app_database.db")
//  return Room.databaseBuilder<AppDatabase>(
//    context = context.applicationContext, name = dbFile.absolutePath
//  ).setDriver(BundledSQLiteDriver()).build()
//}

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
  val appContext = context.applicationContext
  val dbFile = appContext.getDatabasePath("app_database.db")
  return Room.databaseBuilder<AppDatabase>(
    context = appContext,
    name = dbFile.absolutePath
  )
}