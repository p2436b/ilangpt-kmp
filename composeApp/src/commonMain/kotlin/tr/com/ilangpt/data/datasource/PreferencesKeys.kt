package tr.com.ilangpt.data.datasource

import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
  const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"
  val USER = byteArrayPreferencesKey("user")
}