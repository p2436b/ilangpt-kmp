package tr.com.ilangpt.presentation.screen.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import tr.com.ilangpt.domain.model.ChatMessage
import tr.com.ilangpt.presentation.component.ChatBubble
import tr.com.ilangpt.presentation.component.ChatInputBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  onBack: (() -> Unit)? = null,
  viewModel: HomeViewModel = koinViewModel(),
  drawerActions: (DrawerActions?) -> Unit,
  onOpenDrawer: () -> Unit
) {
  val focusManager = LocalFocusManager.current
  val messages by viewModel.messages.collectAsState();
  val searchQuery by viewModel.searchQuery.collectAsState()
  val listState = rememberLazyListState()
  val historyQuery = viewModel.historyQuery.collectAsState()
  val filteredListingHistory = viewModel.filteredListingHistory.collectAsState()

  val actions = remember(viewModel) {
    object : DrawerActions {
      override val historyQuery = historyQuery
      override val filteredListingHistory = filteredListingHistory

      override fun queryHistory(query: String) {
        viewModel.queryHistory(query)
      }

      override fun onHistoryItemClick(item: ChatMessage) {
        println(item.toString())
      }
    }
  }

  LaunchedEffect(Unit) {
    drawerActions(actions)
  }

  DisposableEffect(Unit) {
    onDispose { drawerActions(null) }
  }

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.lastIndex)
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {},
        navigationIcon = {
          IconButton(onClick = onOpenDrawer) {
            Icon(
              imageVector = Icons.Outlined.Menu,
              contentDescription = "Open drawer"
            )
          }
        },
        actions = {
          IconButton(onClick = { viewModel.clearMessages() }) {
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        value = searchQuery,
        onValueChange = { viewModel.updateSearchQuery(it) },
        onSendClick = { viewModel.onSendQuery() }
      )
    }
  ) { padding ->
    Surface(Modifier.fillMaxSize()) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .pointerInput(Unit) {
            detectTapGestures(
              onTap = {
                focusManager.clearFocus()
              }
            )
          },
        contentPadding = PaddingValues(horizontal = 16.dp),
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