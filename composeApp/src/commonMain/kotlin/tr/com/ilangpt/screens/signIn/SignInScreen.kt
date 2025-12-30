package tr.com.ilangpt.screens.signIn

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ilangpt.composeapp.generated.resources.Res
import ilangpt.composeapp.generated.resources.app_name
import ilangpt.composeapp.generated.resources.apple_logo
import ilangpt.composeapp.generated.resources.continue_with_apple
import ilangpt.composeapp.generated.resources.continue_with_google
import ilangpt.composeapp.generated.resources.footer
import ilangpt.composeapp.generated.resources.google_logo
import ilangpt.composeapp.generated.resources.sign_in_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.components.AppButton
import tr.com.ilangpt.components.Logo

@Composable
@Preview
fun SignInScreen(onSignIn: () -> Unit) {
  Scaffold {
    Surface {
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
        AppButton(modifier = Modifier.fillMaxWidth(), onClick = onSignIn) {
          Image(
            painter = painterResource(Res.drawable.google_logo),
            contentDescription = "Google logo"
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(stringResource(Res.string.continue_with_google))
        }
        Spacer(modifier = Modifier.height(24.dp))
        AppButton(modifier = Modifier.fillMaxWidth(), onClick = onSignIn) {
          Image(
            painter = painterResource(Res.drawable.apple_logo),
            contentDescription = "Apple logo"
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(stringResource(Res.string.continue_with_apple))
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



























