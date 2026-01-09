package tr.com.ilangpt.domain.model

import kotlin.time.Clock

data class ChatMessage(
  val id: String = Clock.System.now().toEpochMilliseconds().toString(),
  val url: String? = null,
  val title: String,
  val coverImage: String? = null,
  val websiteName: String? = null,
  val city: String? = null,
  val district: String? = null,
  val neighborhood: String? = null,
  val listingType: String? = null,
  val categoryPath: String? = null,
  val score: Double? = null,
  val isMine: Boolean
)