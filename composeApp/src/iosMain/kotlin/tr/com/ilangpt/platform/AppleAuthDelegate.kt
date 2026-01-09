package tr.com.ilangpt.platform

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationAppleIDCredential
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASPresentationAnchor
import platform.Foundation.NSError
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene
import platform.darwin.NSObject
import tr.com.ilangpt.domain.model.AppleUser

class AppleAuthDelegate(
  private val onSuccess: (AppleUser) -> Unit,
  private val onError: (Throwable) -> Unit
) : NSObject(),
  ASAuthorizationControllerDelegateProtocol,
  ASAuthorizationControllerPresentationContextProvidingProtocol {

  override fun presentationAnchorForAuthorizationController(
    controller: ASAuthorizationController
  ): ASPresentationAnchor {
    val scene = UIApplication.sharedApplication.connectedScenes
      .filterIsInstance<UIWindowScene>()
      .firstOrNull()

    val window = scene?.windows
      ?.filterIsInstance<UIWindow>()
      ?.firstOrNull { it.isKeyWindow() }

    return window ?: error("No key window found")
  }

  @OptIn(ExperimentalForeignApi::class)
  override fun authorizationController(
    controller: ASAuthorizationController,
    didCompleteWithAuthorization: ASAuthorization
  ) {
    val credential =
      didCompleteWithAuthorization.credential as? ASAuthorizationAppleIDCredential
        ?: return

    val userId = credential.user
    val email = credential.email
    val fullName = credential.fullName?.givenName

    onSuccess(
      AppleUser(
        id = userId,
        email = email,
        fullName = fullName
      )
    )
  }

  override fun authorizationController(
    controller: ASAuthorizationController,
    didCompleteWithError: NSError
  ) {
    onError(Throwable(didCompleteWithError.localizedDescription))
  }
}
