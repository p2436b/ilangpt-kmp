package tr.com.ilangpt.navigation

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tr.com.ilangpt.screens.home.HomeScreen
import tr.com.ilangpt.screens.onboarding.OnboardingScreen
import tr.com.ilangpt.screens.privacy.PrivacyPolicyScreen
import tr.com.ilangpt.screens.settings.SettingsScreen
import tr.com.ilangpt.screens.signIn.SignInScreen
import tr.com.ilangpt.screens.terms.TermsOfUseScreen


@Composable
fun MainNavHost(prefs: DataStore<Preferences>) {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = HomeRoute
  ) {
    composable<HomeRoute> {
      HomeScreen(
        onSettings = { navController.navigate(SettingsRoute) },
        onTermsOfUse = { navController.navigate(TermsOfUseRoute) },
        onPrivacyPolicy = { navController.navigate(PrivacyPolicyRoute) }
      )
    }
    composable<TermsOfUseRoute> { backStackEntry -> TermsOfUseScreen() }
    composable<PrivacyPolicyRoute> { backStackEntry -> PrivacyPolicyScreen() }
    composable<SettingsRoute> { backStackEntry -> SettingsScreen() }
  }
}
