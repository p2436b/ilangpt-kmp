package tr.com.ilangpt.data.repository

import tr.com.ilangpt.data.datasource.PreferencesDataSource
import tr.com.ilangpt.domain.model.User
import tr.com.ilangpt.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val dataSource: PreferencesDataSource) :
  PreferencesRepository {

  override suspend fun getUser(): User? {
    return dataSource.getUser()
  }

  override suspend fun saveUser(user: User) {
    dataSource.saveUser(user)
  }

  override suspend fun clearUser() {
    dataSource.clearUser()
  }
}
