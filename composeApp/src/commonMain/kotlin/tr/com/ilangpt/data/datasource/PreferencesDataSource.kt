package tr.com.ilangpt.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import tr.com.ilangpt.domain.model.User

class PreferencesDataSource(private val dataStore: DataStore<Preferences>) {
  suspend fun saveUser(user: User) {
    val userBytes = Json.encodeToString(User.serializer(), user).encodeToByteArray()

    try {
      dataStore.edit { it[PreferencesKeys.USER] = userBytes }
    } catch (e: Exception) {
      if (e is CancellationException) throw e
      println("Error saving token: ${e.message}")
    }
  }

  suspend fun clearUser() {
    dataStore.edit { it.remove(PreferencesKeys.USER) }
  }

  suspend fun getUser(): User? {
    val u = dataStore.data.map { it[PreferencesKeys.USER] }.firstOrNull()
    if (u == null) return null

    return Json.decodeFromString(User.serializer(), u.decodeToString())
  }
}
