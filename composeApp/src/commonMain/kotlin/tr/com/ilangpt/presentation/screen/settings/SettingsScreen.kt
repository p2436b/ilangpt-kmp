package tr.com.ilangpt.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
  val scope = rememberCoroutineScope()
  Scaffold { padding ->
    Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        item {
          SettingsSection(title = "General") {
            SettingsToggle(
              title = "Enable Notifications",
              checked = true,
              onCheckedChange = { checked ->
                // Handle toggle
              }
            )

            SettingsRow(
              title = "Language",
              value = "English",
              onClick = { /* open language selection */ }
            )
          }
        }

        item {
          SettingsSection(title = "About us") {
            SettingsRow(title = "Rate us", onClick = {})
            SettingsRow(title = "Help center", onClick = {})
            SettingsRow(title = "Terms of use", onClick = {})
            SettingsRow(title = "Privacy policy", onClick = {})
          }
        }

        item {
          SettingsSection(title = "Account") {
            SettingsRow(
              title = "Sign Out",
              onClick = { viewModel.signOut() },
              textColor = Color.Red
            )
          }
        }
      }
    }
  }
}


@Composable
fun SettingsSection(
  title: String,
  content: @Composable ColumnScope.() -> Unit
) {
  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier.padding(bottom = 8.dp)
    )
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp),
      content = content
    )
  }
}


@Composable
fun SettingsToggle(
  title: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  var state by remember { mutableStateOf(checked) }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = title, modifier = Modifier.weight(1f))
    Switch(
      checked = state,
      onCheckedChange = {
        state = it
        onCheckedChange(it)
      }
    )
  }
}

@Composable
fun SettingsRow(
  title: String,
  value: String? = null,
  onClick: (() -> Unit)? = null,
  textColor: Color = Color.Unspecified
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(enabled = onClick != null) { onClick?.invoke() }
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = title, color = textColor, modifier = Modifier.weight(1f))
    if (value != null) {
      Text(text = value, color = Color.Gray)
    }
  }
}


