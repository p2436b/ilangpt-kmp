package tr.com.ilangpt.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import tr.com.ilangpt.presentation.screen.onboarding.OnboardingScreen
import tr.com.ilangpt.presentation.screen.privacy.PrivacyPolicyScreen
import tr.com.ilangpt.presentation.screen.signIn.SignInScreen
import tr.com.ilangpt.presentation.screen.terms.TermsOfUseScreen

@Serializable
object AuthGraph

fun NavGraphBuilder.authGraph(
  navController: NavController
) {
  navigation<AuthGraph>(startDestination = SignInRoute) {
    composable<SignInRoute> {
      SignInScreen(onSignIn = { token ->
        //viewModel.onSignInSuccess(token)
      })
    }
    composable<OnboardingRoute> { backStackEntry ->
      OnboardingScreen(onContinue = {
        navController.navigate(
          SignInRoute
        )
      })
    }
    composable<TermsOfUseRoute> { backStackEntry -> TermsOfUseScreen() }
    composable<PrivacyPolicyRoute> { backStackEntry -> PrivacyPolicyScreen() }
  }
}