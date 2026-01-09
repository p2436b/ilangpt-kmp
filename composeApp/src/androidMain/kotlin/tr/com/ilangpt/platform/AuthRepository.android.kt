package tr.com.ilangpt.platform

import tr.com.ilangpt.domain.model.AppleUser

actual object AppleSignIn {
  actual fun signIn(
    onSuccess: (AppleUser) -> Unit,
    onError: (Throwable) -> Unit
  ) {
  }
}