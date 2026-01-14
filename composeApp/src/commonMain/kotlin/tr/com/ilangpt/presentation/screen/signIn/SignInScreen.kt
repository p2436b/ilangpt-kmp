package tr.com.ilangpt.presentation.screen.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import ilangpt.composeapp.generated.resources.Res
import ilangpt.composeapp.generated.resources.app_name
import ilangpt.composeapp.generated.resources.apple_logo
import ilangpt.composeapp.generated.resources.continue_with_apple
import ilangpt.composeapp.generated.resources.continue_with_google
import ilangpt.composeapp.generated.resources.footer
import ilangpt.composeapp.generated.resources.google_logo
import ilangpt.composeapp.generated.resources.sign_in_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import tr.com.ilangpt.data.dto.UserDto
import tr.com.ilangpt.platform.AppleSignIn
import tr.com.ilangpt.platform.Platform
import tr.com.ilangpt.platform.getPlatform
import tr.com.ilangpt.presentation.component.Logo
import tr.com.ilangpt.presentation.component.PrimaryButton

@Composable
@Preview
fun SignInScreen(
  onSignIn: (token: String) -> Unit,
  viewModel: SignInViewModel = koinViewModel()
) {

  var authReady by remember { mutableStateOf(false) }
  val scope = rememberCoroutineScope()

  LaunchedEffect(Unit) {
    val g = GoogleAuthProvider.create(
      credentials = GoogleAuthCredentials(
        serverId = "829540323980-ntqbvpr6fssjucop7138mpkdjcqmqert.apps.googleusercontent.com"
      )
    )
    authReady = true
  }

  Scaffold { padding ->
    Surface(modifier = Modifier.fillMaxSize().padding(padding)) {
      Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(80.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Logo(48.dp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(stringResource(Res.string.app_name), style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
          stringResource(Res.string.sign_in_title),
          style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (authReady) {
          GoogleButtonUiContainer(onGoogleSignInResult = { googleUser ->
            if (googleUser == null) return@GoogleButtonUiContainer

            viewModel.upsertUser(
              UserDto(
                provider = 1,
                email = googleUser.email!!,
                pictureUrl = googleUser.profilePicUrl,
                accessToken = googleUser.accessToken,
                idToken = googleUser.idToken,
                displayName = googleUser.displayName,
                rawProfileJson = "{}"
              )
            )
            onSignIn(googleUser.idToken)
          }) {
            PrimaryButton(modifier = Modifier.fillMaxWidth(), onClick = {
              this.onClick()
            }) {
              Image(
                painter = painterResource(Res.drawable.google_logo),
                contentDescription = "Google logo"
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(stringResource(Res.string.continue_with_google))
            }
          }
        }
        if (getPlatform() == Platform.IOS) {
          Spacer(modifier = Modifier.height(24.dp))
          PrimaryButton(modifier = Modifier.fillMaxWidth(), onClick = {
            AppleSignIn.signIn(
              onSuccess = { user ->
                scope.launch {
                  //prefs.saveToken(user.id)
                }
                println("Apple user: $user")
              },
              onError = {
                println("Apple Sign-In failed: ${it.message}")
              }
            )
          }) {
            Image(
              painter = painterResource(Res.drawable.apple_logo),
              contentDescription = "Apple logo"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(Res.string.continue_with_apple))
          }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
          style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center),
          text = stringResource(Res.string.footer)
        )
      }
    }
  }
}