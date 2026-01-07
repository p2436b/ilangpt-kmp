import platform.AuthenticationServices.*

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
