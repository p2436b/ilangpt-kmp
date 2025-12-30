package tr.com.ilangpt

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ilangpt.composeapp.generated.resources.Res
import ilangpt.composeapp.generated.resources.settings
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.chat.domain.models.ChatMessage
import tr.com.ilangpt.chat.ui.ChatBubble
import tr.com.ilangpt.chat.ui.ChatInputBar
import tr.com.ilangpt.screens.home.HomeScreen
import tr.com.ilangpt.screens.onboarding.OnboardingScreen
import tr.com.ilangpt.screens.settings.SettingsScreen

@Composable
@Preview
fun App() {
  val isDarkTheme = isSystemInDarkTheme()
  val colorScheme = if (isDarkTheme) {
    darkColorScheme()
  } else {
    lightColorScheme()
  }
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography(),
  ) {
    Navigator(OnboardingScreen()) { navigator ->
      val current = navigator.lastItemOrNull // or navigator.current (depending on Voyager version)

      val isHomeScreen = current is HomeScreen //|| current is SearchScreen
      //val showTopBar = current !is OnboardingScreen

      if (isHomeScreen) {
        println("Home Shell")
        HomeShell(navigator) {
          SlideTransition(navigator)
        }
      } else {
        println("Simple Shell")
        SimpleShell() {
          SlideTransition(navigator)
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeShell(navigator: Navigator, content: @Composable () -> Unit) {
  var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
  var input by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.lastIndex)
    }
  }

  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        Row {
          Text(
            text = "Peyman Bayat",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
          )
        }
        NavigationDrawerItem(
          shape = RoundedCornerShape(8.dp),
          label = { Text("Chat") },
          selected = true,
          onClick = { scope.launch { drawerState.close() } },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
          label = { Text(stringResource(Res.string.settings)) },
          selected = false,
          onClick = {
            scope.launch {
              drawerState.close()
              navigator?.push(SettingsScreen())
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
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("", maxLines = 1, overflow = TextOverflow.Ellipsis) },
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
              //onSend(trimmed)
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


@Composable
fun SimpleShell(content: @Composable () -> Unit) {
  Scaffold { padding ->
    Surface(Modifier.fillMaxSize().padding(padding)) {
      content()
    }
  }
}