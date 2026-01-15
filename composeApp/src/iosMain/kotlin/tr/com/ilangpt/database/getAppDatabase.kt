package tr.com.ilangpt.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory
import tr.com.ilangpt.database.entity.database.AppDatabase

fun getAppDatabase(): AppDatabase {
  val dbFile = NSHomeDirectory() + "/app_database.db"
  return Room.databaseBuilder<AppDatabase>(
    name = dbFile, factory = { AppDatabase::class.instantiateImpl() }
  ).setDriver(BundledSQLiteDriver()).build()
}