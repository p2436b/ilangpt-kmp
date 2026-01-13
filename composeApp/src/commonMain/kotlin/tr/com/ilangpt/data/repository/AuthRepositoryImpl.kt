package tr.com.ilangpt.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.domain.model.AuthState
import tr.com.ilangpt.domain.model.User
import tr.com.ilangpt.domain.repository.AuthRepository
import tr.com.ilangpt.domain.repository.PreferencesRepository

class AuthRepositoryImpl(
  private val httpClient: HttpClient,
  private val prefs: PreferencesRepository,
  coroutineScope: CoroutineScope,
) : AuthRepository {

  private val _user = MutableStateFlow<User?>(null)
  override val user: StateFlow<User?> = _user

  override val authState: StateFlow<AuthState> = _user.map {
    AuthState.fromUser(it)
  }.stateIn(
    coroutineScope,
    SharingStarted.WhileSubscribed(5_000),
    AuthState.Loading
  )

  init {
    coroutineScope.launch {
      _user.value = withContext(Dispatchers.IO) {
        prefs.getUser()
      }
    }
  }

  override suspend fun upsertUser(userDto: UserDto): Result<User> {
    val result = withContext(Dispatchers.IO) {
      runCatching {
        val user = httpClient.post("api/auth/oauth/upsert-user") {
          contentType(ContentType.Application.Json)
          setBody(userDto)
        }.body<User>()

        prefs.saveUser(user)
        user
      }
    }
    result.onSuccess { user -> _user.value = user }
    return result
  }

  override suspend fun signOut() {
    withContext(Dispatchers.IO) {
      prefs.clearUser()
    }
    _user.value = null
  }
}