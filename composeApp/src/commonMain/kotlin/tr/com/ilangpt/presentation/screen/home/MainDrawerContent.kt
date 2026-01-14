package tr.com.ilangpt.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.domain.model.ChatMessage
import tr.com.ilangpt.domain.model.User

@Composable
@Preview
fun MainDrawerContentPreview() {
  val listingHistory = listOf(
    ChatMessage(
      title = "Modern Apartment for Rent", isMine = true
    ), ChatMessage(
      title = "Cozy Studio in City Center", isMine = false
    ), ChatMessage(
      title = "Spacious Family House", isMine = true
    ), ChatMessage(
      title = "Luxury Condo with Sea View", isMine = false
    ), ChatMessage(
      title = "Affordable Room Near University", isMine = false
    )
  )

  MainDrawerContent(
    onSettings = {},
    onProfile = {},
    historyQuery = "",
    queryHistory = {},
    user = null,
    filteredListingHistory = listingHistory,
  ) {}
}

@Composable
fun MainDrawerContent(
  onSettings: () -> Unit,
  onProfile: () -> Unit,
  historyQuery: String,
  queryHistory: (String) -> Unit,
  user: User?,
  filteredListingHistory: List<ChatMessage>,
  onHistoryItemClick: (item: ChatMessage) -> Unit,
) {
  val focusManager = LocalFocusManager.current

  ModalDrawerSheet {
    TextField(
      modifier = Modifier.fillMaxWidth().padding(16.dp, 16.dp, 16.dp, 0.dp),
      leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search") },
      trailingIcon = {
        if (historyQuery.isNotEmpty()) {
          IconButton(onClick = { queryHistory("") }) {
            Icon(
              imageVector = Icons.Outlined.Clear, contentDescription = "Clear search"
            )
          }
        }
      },
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
      value = historyQuery,
      onValueChange = { queryHistory(it) })

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
        .padding(8.dp)
        .pointerInput(Unit) {
          detectTapGestures(
            onTap = {
              focusManager.clearFocus()
            }
          )
        }) {
      items(filteredListingHistory) { listing ->
        ListItem(
          modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable { onHistoryItemClick(listing) },
          headlineContent = {
            Text(
              text = listing.title,
              style = MaterialTheme.typography.titleSmall
            )
          })
      }
    }
    HorizontalDivider()
    Row(
      modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      TextButton(onClick = onProfile) {
        user?.pictureUrl?.let {
          AsyncImage(
            modifier = Modifier.size(48.dp).clip(CircleShape),
            model = user.pictureUrl,
            contentDescription = ""
          )
        }
        Spacer(Modifier.width(8.dp))
        Text(text = user?.displayName ?: "No name", style = MaterialTheme.typography.titleMedium)
      }
      Spacer(Modifier.weight(1f))
      IconButton(onClick = onSettings) {
        Icon(
          imageVector = Icons.Outlined.Settings, contentDescription = "Settings"
        )
      }
    }
  }
}