package tr.com.ilangpt.domain.repository

import tr.com.ilangpt.domain.model.User

interface UserRepository {
  suspend fun getUser(id: String): User?
}
