package tr.com.ilangpt.data.datasource

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import tr.com.ilangpt.data.datasource.PreferencesKeys.DATA_STORE_FILE_NAME

actual object DataStoreFileProvider {
  @OptIn(ExperimentalForeignApi::class)
  actual fun preferencesFilePath(): String {
    val directory = NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null
    )
    return requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
  }
}