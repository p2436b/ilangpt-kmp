package tr.com.ilangpt

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.navigation.AppNavHost

@Composable
@Preview
fun App() {
  val isDarkTheme = isSystemInDarkTheme()
  val colorScheme = if (isDarkTheme) {
    darkColorScheme()
  } else {
    lightColorScheme()
  }
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography(),
  ) {
    val navController = rememberNavController()
    AppNavHost(navController)
  }
}