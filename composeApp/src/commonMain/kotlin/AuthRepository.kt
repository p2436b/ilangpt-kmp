data class AppleUser(
    val id: String,
    val email: String?,
    val fullName: String?
)

expect object AppleSignIn {
    fun signIn(
        onSuccess: (AppleUser) -> Unit,
        onError: (Throwable) -> Unit
    )
}
