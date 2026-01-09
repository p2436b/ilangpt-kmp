package tr.com.ilangpt.data.repository

import io.ktor.client.HttpClient
import tr.com.ilangpt.domain.model.User
import tr.com.ilangpt.domain.repository.UserRepository

class UserRepositoryImpl(private val httpClient: HttpClient) : UserRepository {
  override suspend fun getUser(id: String): User? {
    return User("id12345", "Fatma Shan")
  }
}