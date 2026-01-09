package tr.com.ilangpt.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ilangpt.composeapp.generated.resources.Res
import ilangpt.composeapp.generated.resources.logo_dark
import ilangpt.composeapp.generated.resources.logo_light
import org.jetbrains.compose.resources.painterResource

@Composable
fun Logo(size: Dp) {
  val isDarkTheme = isSystemInDarkTheme()

  Image(
    modifier = Modifier.width(size).aspectRatio(1f),
    painter = painterResource(if (isDarkTheme) Res.drawable.logo_dark else Res.drawable.logo_light),
    contentDescription = "Logo"
  )
}