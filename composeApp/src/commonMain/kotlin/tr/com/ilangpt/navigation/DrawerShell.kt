package tr.com.ilangpt.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import cafe.adriel.voyager.navigator.LocalNavigator
import tr.com.ilangpt.screens.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerShell(
  title: String,
  content: @Composable (PaddingValues) -> Unit
) {
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  val navigator = LocalNavigator.current

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        Text(
          title,
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(16.dp)
        )


        NavigationDrawerItem(
          label = { Text("Home") },
          selected = true,
          onClick = { scope.launch { drawerState.close() } },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
          label = { Text("Settings") },
          selected = false,
          onClick = {
            scope.launch { drawerState.close() }
            navigator?.push(SettingsScreen())
          },
          modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text(title) },
          navigationIcon = {
            // KMP-safe opener
            TextButton(onClick = { scope.launch { drawerState.open() } }) { Text("Menu") }
          }
        )
      }

    ) { padding ->
      content(padding)
    }
  }
}
