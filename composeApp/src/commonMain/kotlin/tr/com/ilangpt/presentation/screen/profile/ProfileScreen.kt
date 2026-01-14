package tr.com.ilangpt.presentation.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun ProfileScreen(
  onTermsOfUse: () -> Unit,
  onPrivacyPolicy: () -> Unit,
  viewModel: ProfileViewModel = koinViewModel()
) {
  val user by viewModel.user.collectAsState()
  Scaffold { padding ->
    Column(
      Modifier.fillMaxSize().padding(padding).padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(Modifier.height(16.dp))
      user?.pictureUrl?.let { url ->
        AsyncImage(
          modifier = Modifier.size(158.dp).clip(CircleShape),
          model = url,
          contentDescription = "Profile picture"
        )
      }
      Spacer(Modifier.height(16.dp))
      Text(user?.displayName ?: "No name", style = MaterialTheme.typography.titleLarge)
      Spacer(Modifier.weight(1f))
      OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onTermsOfUse) {
        Text("Term of use")
      }
      OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onPrivacyPolicy) {
        Text("Privacy policy")
      }
      Spacer(Modifier.height(32.dp))
      OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.signOut() }) {
        Text("Sign out", color = MaterialTheme.colorScheme.error)
      }
    }
  }
}