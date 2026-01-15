package tr.com.ilangpt.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
  val lastRoutedState = remember { mutableStateOf<AuthState?>(null) }

  LaunchedEffect(authState) {
    if (authState == lastRoutedState.value) return@LaunchedEffect
    lastRoutedState.value = authState

    when (authState) {
      AuthState.Authenticated ->
        navController.navigate(MainGraph) {
          popUpTo(0) { inclusive = true }
          launchSingleTop = true
        }

      AuthState.Unauthenticated ->
        navController.navigate(AuthGraph) {
          popUpTo(0) { inclusive = true }
          launchSingleTop = true
        }

      AuthState.Loading -> Unit
    }
  }

  NavHost(
    navController = navController,
    startDestination = RootGraph
  ) {
    composable<RootGraph> { Box(modifier = Modifier.fillMaxSize()) {} }
    authGraph(navController)
    mainGraph(
      navController = navController,
      drawerActions = drawerActions,
      onOpenDrawer = onOpenDrawer
    )
  }
}