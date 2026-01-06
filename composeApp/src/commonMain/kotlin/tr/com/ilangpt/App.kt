package tr.com.ilangpt

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import tr.com.ilangpt.navigation.AppNavHost
import tr.com.ilangpt.theme.AppTheme

@Composable
@Preview
fun App() {
  AppTheme {
    val navController = rememberNavController()
    AppNavHost(navController)
  }
}