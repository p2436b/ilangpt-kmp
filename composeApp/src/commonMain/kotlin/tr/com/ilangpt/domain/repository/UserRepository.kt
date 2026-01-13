package tr.com.ilangpt.domain.repository

import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.model.User

interface UserRepository {
  suspend fun upsertUser(userDto: UserDto): User?
}
