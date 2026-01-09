package tr.com.ilangpt.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSource(private val dataStore: DataStore<Preferences>) {
  val token: Flow<String?> = dataStore.data.map { it[PreferencesKeys.TOKEN] }

  suspend fun saveToken(token: String) {
    try {
      dataStore.edit { it[PreferencesKeys.TOKEN] = token }
    } catch (e: Exception) {
      println("Error saving token: ${e.message}")
    }
  }

  suspend fun clearToken() {
    dataStore.edit { it.remove(PreferencesKeys.TOKEN) }
  }
}
