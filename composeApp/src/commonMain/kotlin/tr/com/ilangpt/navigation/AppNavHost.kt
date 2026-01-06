package tr.com.ilangpt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tr.com.ilangpt.screens.home.HomeScreen
import tr.com.ilangpt.screens.onboarding.OnboardingScreen
import tr.com.ilangpt.screens.privacy.PrivacyPolicyScreen
import tr.com.ilangpt.screens.settings.SettingsScreen
import tr.com.ilangpt.screens.signIn.SignInScreen
import tr.com.ilangpt.screens.terms.TermsOfUseScreen


@Composable
fun AppNavHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = OnboardingRoute
  ) {
    composable<OnboardingRoute> { OnboardingScreen(onContinue = { navController.navigate(SignInRoute) }) }
    composable<SignInRoute> {
      SignInScreen(onSignIn = {
        navController.navigate(HomeRoute) {
          // If you don't want onboarding in back stack
          popUpTo(OnboardingRoute) { inclusive = true }
        }
      })
    }
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
