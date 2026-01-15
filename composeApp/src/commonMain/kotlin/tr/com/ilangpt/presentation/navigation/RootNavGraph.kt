package tr.com.ilangpt.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import tr.com.ilangpt.domain.model.AuthState
import tr.com.ilangpt.presentation.screen.home.DrawerActions

@Serializable
object RootGraph

@Composable
fun RootNavGraph(
  authState: AuthState,
  navController: NavHostController,
  drawerActions: (DrawerActions?) -> Unit,
  onOpenDrawer: () -> Unit
) {
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
    composable<RootGraph> { }
    authGraph(navController)
    mainGraph(
      navController = navController,
      drawerActions = drawerActions,
      onOpenDrawer = onOpenDrawer
    )
  }
}