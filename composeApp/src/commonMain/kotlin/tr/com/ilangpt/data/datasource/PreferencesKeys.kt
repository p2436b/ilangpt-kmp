package tr.com.ilangpt.data.datasource

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
  const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"
  const val AUTH_TOKEN = "auth_token"
  val TOKEN = stringPreferencesKey("token")
}