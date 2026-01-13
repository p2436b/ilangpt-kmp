package tr.com.ilangpt.domain.model

sealed interface AuthState {
  data object Loading : AuthState
  data object Unauthenticated : AuthState
  data object Authenticated : AuthState

  companion object {
    fun fromUser(user: User?): AuthState =
      if (user == null) Unauthenticated else Authenticated
  }
}