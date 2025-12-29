package tr.com.ilangpt.chat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import tr.com.ilangpt.chat.domain.models.ChatMessage
import kotlin.time.Instant

@Composable
public fun ChatBubble(message: ChatMessage) {
    val align = if (message.isMine) Alignment.End else Alignment.Start

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = align
    ) {
        if (!message.isMine && !message.senderName.isNullOrBlank()) {
            Text(
                text = message.senderName,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
            Spacer(Modifier.height(2.dp))
        }

        val bg = if (message.isMine)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant

        val fg = if (message.isMine)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurfaceVariant

        Surface(
            color = bg,
            shape = RoundedCornerShape(
                topStart = 18.dp, topEnd = 18.dp,
                bottomStart = if (message.isMine) 18.dp else 6.dp,
                bottomEnd = if (message.isMine) 6.dp else 18.dp
            )
        ) {
            Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(message.text, color = fg)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = formatTime(message.timestampMillis),
                    style = MaterialTheme.typography.labelSmall,
                    color = fg.copy(alpha = 0.75f),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

private fun formatTime(timestampMillis: Long): String {
    val seconds = Instant
        .fromEpochMilliseconds(timestampMillis)
        .epochSeconds % 86_400

    val h = (seconds / 3600).toString().padStart(2, '0')
    val m = ((seconds % 3600) / 60).toString().padStart(2, '0')

    return "$h:$m"
}