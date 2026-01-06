package tr.com.ilangpt.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val lightScheme = lightColorScheme(
  primary = Primary,
  onPrimary = OnPrimary,
  surface = Surface,
  onSurface = OnSurface,
  background = Surface,
  onBackground = OnSurface
)

val darkScheme = darkColorScheme(
  primary = PrimaryDark,
  onPrimary = OnPrimaryDark,
  surface = SurfaceDark,
  onSurface = OnSurfaceDark,
  background = SurfaceDark,
  onBackground = OnSurfaceDark
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  val theme = if (isSystemInDarkTheme()) darkScheme else lightScheme
  MaterialTheme(colorScheme = theme, content = content)
}