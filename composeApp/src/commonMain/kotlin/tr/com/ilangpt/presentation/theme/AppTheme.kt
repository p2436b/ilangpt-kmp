package tr.com.ilangpt.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val lightScheme = lightColorScheme(
  primary = Primary,
  onPrimary = OnPrimary,
  surface = Surface,
  onSurface = OnSurface,
  background = Surface,
  onBackground = OnSurface,
  outline = Outline,
  primaryContainer = Color.Red
)

val darkScheme = darkColorScheme(
  primary = PrimaryDark,
  onPrimary = OnPrimaryDark,
  surface = SurfaceDark,
  onSurface = OnSurfaceDark,
  background = SurfaceDark,
  onBackground = OnSurfaceDark,
  outline = OutlineDark
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  val theme = if (isSystemInDarkTheme()) darkScheme else lightScheme
  MaterialTheme(colorScheme = theme, content = content)
}