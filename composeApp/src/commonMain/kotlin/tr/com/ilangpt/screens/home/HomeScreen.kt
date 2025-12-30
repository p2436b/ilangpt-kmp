package tr.com.ilangpt.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tr.com.ilangpt.chat.domain.models.ChatMessage
import tr.com.ilangpt.chat.ui.ChatBubble
import tr.com.ilangpt.chat.ui.ChatInputBar
import tr.com.ilangpt.components.Logo
import tr.com.ilangpt.network.ListingApi
import tr.com.ilangpt.network.createHttpClient
import tr.com.ilangpt.network.dto.ListingDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  title: String = "Chat",
  initialMessages: List<ChatMessage> = emptyList(),
  onBack: (() -> Unit)? = null,
  onSend: (String) -> Unit = {},
  onSettings: () -> Unit
) {
  val api = ListingApi(
    createHttpClient(),
    baseUrl = "https://post-gpt-backend-bte3h3ftg3hgf2cu.westeurope-01.azurewebsites.net"
  )
  var messages by remember { mutableStateOf(initialMessages) }
  var input by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.lastIndex)
    }
  }

  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  var results by remember { mutableStateOf<List<ListingDto>>(emptyList()) }
  var isLoading by remember { mutableStateOf(false) }
  var error by remember { mutableStateOf<String?>(null) }

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
          Logo(48.dp)
          Text(
            text = "Peyman Bayat",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
          )
        }
        Spacer(Modifier.height(12.dp))
        NavigationDrawerItem(
          shape = RoundedCornerShape(8.dp),
          label = { Text("Chat") },
          selected = true,
          onClick = { scope.launch { drawerState.close() } },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
          label = { Text("Settings") },
          selected = false,
          onClick = {
            scope.launch {
              drawerState.close()
              onSettings()
            }
          },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
          label = { Text("About") },
          selected = false,
          onClick = { scope.launch { drawerState.close() } },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        Spacer(Modifier.weight(1f))
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = "version 1.0.0",
          style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
        )
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
          navigationIcon = {
            IconButton(
              onClick = { scope.launch { drawerState.open() } }
            ) {
              Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "Open drawer"
              )
            }
          },
          actions = {
            IconButton(
              onClick = { }
            ) {
              Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "New chat"
              )
            }
          }
        )
      },
      bottomBar = {
        ChatInputBar(
          value = input,
          onValueChange = { input = it },
          onSendClick = {
            val trimmed = input.trim()
            if (trimmed.isNotEmpty()) {
              messages = messages + ChatMessage(text = trimmed, isMine = true)
              onSend(trimmed)

              isLoading = true
              error = null

              scope.launch {
                try {
                  val data = api.searchListings(trimmed)
                  //
                  // results = data
                  data.forEach {
                    messages = messages + ChatMessage(id = it.id.toString(),text = it.title, isMine = false)
                  }
                  isLoading = false
                } catch (t: Throwable) {
                  error = t.message ?: "Unknown error"
                  isLoading = false
                }
              }

              input = ""
            }
          }
        )
      }
    ) { padding ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .padding(horizontal = 12.dp, vertical = 12.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(messages, key = { it.id }) { msg ->
          ChatBubble(msg)
        }
      }
    }
  }
}