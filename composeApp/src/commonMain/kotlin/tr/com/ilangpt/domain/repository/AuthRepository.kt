package tr.com.ilangpt.domain.repository

import kotlinx.coroutines.flow.StateFlow
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.model.AuthState
import tr.com.ilangpt.domain.model.User

interface AuthRepository {

  val authState: StateFlow<AuthState>
  val user: StateFlow<User?>

  suspend fun upsertUser(userDto: UserDto): Result<User>
  suspend fun signOut()
}
