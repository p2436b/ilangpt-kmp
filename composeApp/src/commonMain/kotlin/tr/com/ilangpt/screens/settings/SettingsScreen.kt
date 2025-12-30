package tr.com.ilangpt.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen() {
  Scaffold { padding ->
    Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
      Text("Settings")
    }
  }
}