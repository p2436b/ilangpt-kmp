package tr.com.ilangpt.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import tr.com.ilangpt.domain.model.ChatMessage

@Composable
public fun ChatBubble(message: ChatMessage) {
  val align = if (message.isMine) Alignment.End else Alignment.Start
  val borderColor = Color.Gray
  val bubbleShape = RoundedCornerShape(
    topStart = 12.dp,
    topEnd = 12.dp,
    bottomStart = if (message.isMine) 12.dp else 0.dp,
    bottomEnd = if (message.isMine) 0.dp else 12.dp
  )

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = align
  ) {
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
      modifier = Modifier
        .border(width = 1.dp, color = borderColor, shape = bubbleShape),
      shape = bubbleShape
    ) {
      if (message.isMine) {
        Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
          Text(message.title)
        }
      } else {
        Column {
          Box(Modifier.fillMaxWidth().height(150.dp)) {
            message.coverImage?.let {
              AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                model = message.coverImage,
                contentDescription = null,
              )
              Spacer(Modifier.height(16.dp))
            }
            message.listingType?.let {
              Text(
                modifier = Modifier
                  .align(Alignment.TopStart)
                  .padding(8.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(color = MaterialTheme.colorScheme.surface)
                  .padding(vertical = 4.dp, horizontal = 8.dp),
                text = message.listingType,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall
              )
            }
            message.websiteName?.let {
              Text(
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .padding(8.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(color = MaterialTheme.colorScheme.surface)
                  .padding(vertical = 4.dp, horizontal = 8.dp),
                text = message.websiteName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall
              )
            }
          }
          Row(Modifier.padding(horizontal = 8.dp)) {
            message.city?.let {
              Text(
                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                text = message.city,
                style = MaterialTheme.typography.titleSmall
              )
            }
            message.district?.let {
              Text(
                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                text = message.district,
                style = MaterialTheme.typography.titleSmall
              )
            }
            message.neighborhood?.let {
              Text(
                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                text = message.neighborhood,
                style = MaterialTheme.typography.titleSmall
              )
            }
          }
          Text(
            modifier = Modifier.padding(8.dp),
            text = message.title,
            color = fg,
            style = MaterialTheme.typography.titleMedium
          )
          message.url?.let {
            val uriHandler = LocalUriHandler.current
            Text(
              text = message.url,
              color = Color(0xFF00C4FF),
              textDecoration = TextDecoration.Underline,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = Modifier.padding(8.dp).clickable {
                uriHandler.openUri(message.url)
              }
            )
          }
        }
      }
    }
  }
}
