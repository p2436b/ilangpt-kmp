package tr.com.ilangpt.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tr.com.ilangpt.data.datasource.PreferencesDataSource
import tr.com.ilangpt.domain.model.AuthState
import tr.com.ilangpt.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val dataSource: PreferencesDataSource) :
  PreferencesRepository {

  override val authState: Flow<AuthState> =
    dataSource.token.map { token ->
      if (token.isNullOrBlank()) {
        AuthState.Unauthenticated
      } else {
        AuthState.Authenticated
      }
    }

  override suspend fun saveToken(token: String) {
    dataSource.saveToken(token)
  }

  override suspend fun clearToken() {
    dataSource.clearToken()
  }
}
