package tr.com.ilangpt.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSUserDomainMask
import tr.com.ilangpt.database.database.AppDatabase

//fun getAppDatabase(): AppDatabase {
//  val dbFile = NSHomeDirectory() + "/app_database.db"
//  return Room.databaseBuilder<AppDatabase>(
//    name = dbFile, factory = { AppDatabase::class.instantiateImpl() }
//  ).setDriver(BundledSQLiteDriver()).build()
//}


fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
  val dbFilePath = documentDirectory() + "/app_database.db"
  return Room.databaseBuilder<AppDatabase>(
    name = dbFilePath,
  )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
  )
  return requireNotNull(documentDirectory?.path)
}