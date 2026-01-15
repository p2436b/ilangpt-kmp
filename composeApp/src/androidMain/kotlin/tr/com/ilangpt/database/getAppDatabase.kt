package tr.com.ilangpt.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import tr.com.ilangpt.database.entity.database.AppDatabase

fun getAppDatabase(context: Context): AppDatabase {
  val dbFile = context.getDatabasePath("app_database.db")
  return Room.databaseBuilder<AppDatabase>(
    context = context.applicationContext, name = dbFile.absolutePath
  ).setDriver(BundledSQLiteDriver()).build()
}