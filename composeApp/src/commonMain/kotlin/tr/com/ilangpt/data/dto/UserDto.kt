package tr.com.ilangpt.data.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class UserDto(
  val provider: Int,
  val providerSubject: String? = null,
  val email: String,
  val displayName: String? = null,
  val givenName: String? = null,
  val familyName: String? = null,
  val pictureUrl: String? = null,
  val emailVerified: Boolean? = false,
  val rawProfileJson: String? = null,
  val accessToken: String? = null,
  val accessTokenExpiresAt: Instant? = null,
  val refreshToken: String? = null,
  val idToken: String? = null,
)