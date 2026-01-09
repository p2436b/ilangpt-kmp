package tr.com.ilangpt.presentation.screen.home

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
import androidx.compose.runtime.collectAsState
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
import org.koin.compose.viewmodel.koinViewModel
import tr.com.ilangpt.domain.model.ChatMessage
import tr.com.ilangpt.network.ListingApi
import tr.com.ilangpt.network.createHttpClient
import tr.com.ilangpt.network.dto.ListingDto
import tr.com.ilangpt.presentation.component.ChatBubble
import tr.com.ilangpt.presentation.component.ChatInputBar
import tr.com.ilangpt.presentation.component.Logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  title: String = "",
  onBack: (() -> Unit)? = null,
  onSend: (String) -> Unit = {},
  onSettings: () -> Unit,
  onTermsOfUse: () -> Unit,
  onPrivacyPolicy: () -> Unit,
  viewModel: HomeViewModel = koinViewModel()
) {
  val api = ListingApi(
    createHttpClient(),
    baseUrl = "https://post-gpt-backend-bte3h3ftg3hgf2cu.westeurope-01.azurewebsites.net"
  )
  val messages by viewModel.messages.collectAsState();
  val input by viewModel.input.collectAsState()
  val user by viewModel.user.collectAsState()
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
            text = user?.name ?: "No name",
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
          label = { Text("Terms of use") },
          selected = false,
          onClick = {
            scope.launch {
              drawerState.close()
              onTermsOfUse()
            }
          },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
          label = { Text("Privacy policy") },
          selected = false,
          onClick = {
            scope.launch {
              drawerState.close()
              onPrivacyPolicy()
            }
          },
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
              onClick = { viewModel.clearMessages() }
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
          onValueChange = { viewModel.updateInput(it) },
          onSendClick = {
            val trimmed = input.trim()
            if (trimmed.isNotEmpty()) {
              viewModel.updateMessages(ChatMessage(title = trimmed, isMine = true))
              onSend(trimmed)
              isLoading = true
              error = null

              scope.launch {
                try {
                  val data = api.searchListings(trimmed)
                  data.forEach {
                    viewModel.updateMessages(
                      ChatMessage(
                        id = it.id.toString(),
                        title = it.title,
                        url = it.url,
                        coverImage = it.coverImage,
                        websiteName = it.websiteName,
                        city = it.city,
                        district = it.district,
                        neighborhood = it.neighborhood,
                        listingType = it.listingType,
                        categoryPath = it.categoryPath,
                        score = it.score,
                        isMine = false
                      )
                    )
                  }
                  isLoading = false
                } catch (t: Throwable) {
                  error = t.message ?: "Unknown error"
                  isLoading = false
                }
              }

              viewModel.updateInput("")
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