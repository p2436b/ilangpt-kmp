package tr.com.ilangpt.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import tr.com.ilangpt.domain.model.User

class PreferencesDataSource(private val dataStore: DataStore<Preferences>) {
  val token: Flow<String?> = dataStore.data.map { it[PreferencesKeys.TOKEN] }
  val user: Flow<User?> = dataStore.data.map { prefs ->
    val u = prefs[PreferencesKeys.USER] ?: return@map null
    return@map Json.decodeFromString(User.serializer(), u.decodeToString())
  }

  suspend fun saveToken(token: String) {
    try {
      dataStore.edit { it[PreferencesKeys.TOKEN] = token }
    } catch (e: Exception) {
      if (e is CancellationException) throw e
      println("Error saving token: ${e.message}")
    }
  }

  suspend fun clearToken() {
    dataStore.edit { it.remove(PreferencesKeys.TOKEN) }
  }

  suspend fun saveUser(user: User) {
    val userBytes = Json.encodeToString(User.serializer(), user).encodeToByteArray()

    try {
      dataStore.edit { it[PreferencesKeys.USER] = userBytes }
    } catch (e: Exception) {
      if (e is CancellationException) throw e
      println("Error saving token: ${e.message}")
    }
  }
}
