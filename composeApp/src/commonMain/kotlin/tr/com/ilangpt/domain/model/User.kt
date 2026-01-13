package tr.com.ilangpt.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class User(
  val id: String,
  val provider: Int,
  val providerSubject: String,
  val email: String,
  val emailVerified: Boolean,
  val displayName: String,
  val givenName: String,
  val familyName: String,
  val pictureUrl: String,
  val createdAt: Instant,
  val updatedAt: Instant,
)