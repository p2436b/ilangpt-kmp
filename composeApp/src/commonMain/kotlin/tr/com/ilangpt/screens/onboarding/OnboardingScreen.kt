package tr.com.ilangpt.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import ilangpt.composeapp.generated.resources.Res
import ilangpt.composeapp.generated.resources.app_name
import ilangpt.composeapp.generated.resources.building
import ilangpt.composeapp.generated.resources.car
import ilangpt.composeapp.generated.resources.car_description
import ilangpt.composeapp.generated.resources.`continue`
import ilangpt.composeapp.generated.resources.footer
import ilangpt.composeapp.generated.resources.real_estate
import ilangpt.composeapp.generated.resources.real_estate_description
import ilangpt.composeapp.generated.resources.welcome
import ilangpt.composeapp.generated.resources.what_can_you_do
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.components.AppButton
import tr.com.ilangpt.components.Logo

@Composable
@Preview
fun OnboardingScreen(onContinue: () -> Unit) {
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
        Text(stringResource(Res.string.welcome), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
          stringResource(Res.string.what_can_you_do),
          style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Item(
          stringResource(Res.string.real_estate),
          stringResource(Res.string.real_estate_description),
          icon = Res.drawable.building
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))
        Item(
          stringResource(Res.string.car),
          stringResource(Res.string.car_description),
          icon = Res.drawable.car
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(modifier = Modifier.fillMaxWidth(), onClick = onContinue) {
          Text(stringResource(Res.string.`continue`))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
          stringResource(Res.string.footer),
          style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center),
        )
      }
    }
  }
}


@Composable
fun Item(title: String, body: String, icon: DrawableResource) {
  Column {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        modifier = Modifier.width(40.dp).aspectRatio(1f),
        painter = painterResource(icon),
        contentDescription = title
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(title, style = MaterialTheme.typography.titleMedium)
    }
    Spacer(modifier = Modifier.height(12.dp))
    Text(body, style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 2.em))
  }
}





























