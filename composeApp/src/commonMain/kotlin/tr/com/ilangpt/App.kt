package tr.com.ilangpt

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.navigation.AuthNavHost
import tr.com.ilangpt.navigation.MainNavHost
import tr.com.ilangpt.theme.AppTheme

@Composable
@Preview
fun App(prefs: DataStore<Preferences>) {
  val authState by prefs.data.map {
    val tokenId = it[stringPreferencesKey("token")]
    if (tokenId.isNullOrBlank()) {
      AuthState.Unauthenticated
    } else {
      AuthState.Authenticated
    }
  }.collectAsState(AuthState.Loading)

  AppTheme {
    when (authState) {
      AuthState.Loading -> {
        // Splash screen
        Box(Modifier.fillMaxSize()) {
          Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            CircularProgressIndicator()
            Spacer(Modifier.height(12.dp))
            Text("Loading...")
          }
        }
      }

      AuthState.Unauthenticated -> AuthNavHost(prefs)
      AuthState.Authenticated -> MainNavHost(prefs)
    }
  }
}

sealed interface AuthState {
  object Loading : AuthState
  object Unauthenticated : AuthState
  object Authenticated : AuthState
}