package tr.com.ilangpt.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.model.User
import tr.com.ilangpt.domain.repository.UserRepository

class UserRepositoryImpl(private val httpClient: HttpClient) : UserRepository {
  override suspend fun upsertUser(userDto: UserDto): User? {
    return httpClient.post("api/auth/oauth/upsert-user") {
      contentType(ContentType.Application.Json)
      setBody(userDto)
    }.body<User>()
  }
}