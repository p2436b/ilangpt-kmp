package tr.com.ilangpt.presentation.screen.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import tr.com.ilangpt.presentation.component.ChatBubble
import tr.com.ilangpt.presentation.component.ChatInputBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  onBack: (() -> Unit)? = null,
  onSettings: () -> Unit,
  onProfile: () -> Unit,
  viewModel: HomeViewModel = koinViewModel()
) {
  val focusManager = LocalFocusManager.current
  val messages by viewModel.messages.collectAsState();
  val filteredListingHistory by viewModel.filteredListingHistory.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val user by viewModel.user.collectAsState()
  val listState = rememberLazyListState()
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  val historyQuery by viewModel.historyQuery.collectAsState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.lastIndex)
    }
  }

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      MainDrawerContent(
        onSettings = onSettings,
        onProfile = onProfile,
        historyQuery = historyQuery,
        queryHistory = { viewModel.queryHistory(it) },
        filteredListingHistory = filteredListingHistory,
        onHistoryItemClick = {
          scope.launch {
            drawerState.close()
          }
          focusManager.clearFocus()
        },
        user = user,
      )
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {},
          navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
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
          value = searchQuery,
          onValueChange = { viewModel.updateSearchQuery(it) },
          onSendClick = { viewModel.onSendQuery() }
        )
      }
    ) { padding ->
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .padding(horizontal = 12.dp, vertical = 12.dp)
          .pointerInput(Unit) {
            detectTapGestures(
              onTap = {
                focusManager.clearFocus()
              }
            )
          },
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