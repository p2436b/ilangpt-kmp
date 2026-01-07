import kotlinx.cinterop.ExperimentalForeignApi
import platform.AuthenticationServices.*
import platform.Foundation.*
import platform.UIKit.*
import platform.darwin.NSObject

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
