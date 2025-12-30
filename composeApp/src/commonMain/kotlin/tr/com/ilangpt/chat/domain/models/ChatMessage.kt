package tr.com.ilangpt.chat.domain.models

import kotlin.time.Clock

data class ChatMessage(
  val id: String = Clock.System.now().toEpochMilliseconds().toString(),
  val text: String,
  val isMine: Boolean,
  val timestampMillis: Long = Clock.System.now().toEpochMilliseconds(),
  val senderName: String? = null
)
