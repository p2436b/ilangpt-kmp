package tr.com.ilangpt.platform

import tr.com.ilangpt.domain.model.AppleUser

expect object AppleSignIn {
  fun signIn(
    onSuccess: (AppleUser) -> Unit,
    onError: (Throwable) -> Unit
  )
}
