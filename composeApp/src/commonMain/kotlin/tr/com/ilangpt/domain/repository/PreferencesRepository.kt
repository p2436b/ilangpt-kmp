package tr.com.ilangpt.domain.repository

import kotlinx.coroutines.flow.Flow
import tr.com.ilangpt.domain.model.AuthState

interface PreferencesRepository {
  val authState: Flow<AuthState>
  suspend fun saveToken(token: String)
  suspend fun clearToken()
}