package tr.com.ilangpt.data.datasource

import android.content.Context
import tr.com.ilangpt.data.datasource.PreferencesKeys.DATA_STORE_FILE_NAME

lateinit var appContext: Context

actual object DataStoreFileProvider {
  actual fun preferencesFilePath(): String {
    return appContext
      .filesDir
      .resolve(DATA_STORE_FILE_NAME)
      .absolutePath
  }
}