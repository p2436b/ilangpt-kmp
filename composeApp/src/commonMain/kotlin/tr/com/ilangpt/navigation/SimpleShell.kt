package tr.com.ilangpt.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleShell(
  title: String,
  showBack: Boolean,
  onBack: () -> Unit,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(title) },
        navigationIcon = {
          if (showBack) {
            TextButton(onClick = onBack) { Text("Back") }
          }
        }
      )
    }
  ) { padding ->
    content(padding)
  }
}
