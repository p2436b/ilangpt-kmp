package tr.com.ilangpt.domain.repository

import tr.com.ilangpt.domain.model.User

interface PreferencesRepository {
  suspend fun getUser(): User?
  suspend fun saveUser(user: User)
  suspend fun clearUser()
}