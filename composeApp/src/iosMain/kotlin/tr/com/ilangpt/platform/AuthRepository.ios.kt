package tr.com.ilangpt.platform

import platform.AuthenticationServices.ASAuthorizationAppleIDProvider
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationScopeEmail
import platform.AuthenticationServices.ASAuthorizationScopeFullName
import tr.com.ilangpt.domain.model.AppleUser

actual object AppleSignIn {

  // 🔴 KEEP A STRONG REFERENCE
  private var currentDelegate: AppleAuthDelegate? = null

  actual fun signIn(
    onSuccess: (AppleUser) -> Unit,
    onError: (Throwable) -> Unit
  ) {
    val provider = ASAuthorizationAppleIDProvider()
    val request = provider.createRequest().apply {
      requestedScopes = listOf(
        ASAuthorizationScopeFullName,
        ASAuthorizationScopeEmail
      )
    }

    val delegate = AppleAuthDelegate(
      onSuccess = {
        currentDelegate = null
        onSuccess(it)
      },
      onError = {
        currentDelegate = null
        onError(it)
      }
    )

    currentDelegate = delegate

    val controller = ASAuthorizationController(listOf(request))
    controller.delegate = delegate
    controller.presentationContextProvider = delegate
    controller.performRequests()
  }
}
