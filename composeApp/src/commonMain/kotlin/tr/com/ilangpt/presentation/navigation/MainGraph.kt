package tr.com.ilangpt.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import tr.com.ilangpt.presentation.screen.home.HomeScreen
import tr.com.ilangpt.presentation.screen.privacy.PrivacyPolicyScreen
import tr.com.ilangpt.presentation.screen.settings.SettingsScreen
import tr.com.ilangpt.presentation.screen.terms.TermsOfUseScreen

@Serializable
object MainGraph

fun NavGraphBuilder.mainGraph(
  navController: NavController
) {
  navigation<MainGraph>(startDestination = HomeRoute) {
    composable<HomeRoute> {
      HomeScreen(
        onSettings = { navController.navigate(SettingsRoute) },
        onTermsOfUse = { navController.navigate(TermsOfUseRoute) },
        onPrivacyPolicy = { navController.navigate(PrivacyPolicyRoute) }
      )
    }
    composable<TermsOfUseRoute> { backStackEntry -> TermsOfUseScreen() }
    composable<PrivacyPolicyRoute> { backStackEntry -> PrivacyPolicyScreen() }
    composable<SettingsRoute> { SettingsScreen() }
  }
}