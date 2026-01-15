package tr.com.ilangpt.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import tr.com.ilangpt.domain.model.AuthState

@Serializable
object RootGraph

@Composable
fun RootNavGraph(authState: AuthState, navController1: NavHostController) {
  val navController = rememberNavController()

  // Global routing based on auth state
  LaunchedEffect(authState) {
    when (authState) {
      AuthState.Authenticated ->
        navController.navigate(MainGraph) {
          popUpTo(RootGraph) { inclusive = true }
          launchSingleTop = true
        }

      AuthState.Unauthenticated ->
        navController.navigate(AuthGraph) {
          popUpTo(RootGraph) { inclusive = true }
          launchSingleTop = true
        }

      AuthState.Loading -> Unit
    }
  }

  NavHost(
    navController = navController,
    startDestination = RootGraph
  ) {
    composable<RootGraph> {
      // Splash screen
//      Box(Modifier.fillMaxSize()) {
//        Column(
//          Modifier.align(Alignment.Center),
//          horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//          CircularProgressIndicator()
//          Spacer(Modifier.height(12.dp))
//          Text("Loading...")
//        }
//      }

    }

    authGraph(navController)
    mainGraph(navController)
  }
}