package tr.com.ilangpt

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import tr.com.ilangpt.domain.repository.AuthRepository
import tr.com.ilangpt.presentation.navigation.HomeRoute
import tr.com.ilangpt.presentation.navigation.ProfileRoute
import tr.com.ilangpt.presentation.navigation.RootNavGraph
import tr.com.ilangpt.presentation.navigation.SettingsRoute
import tr.com.ilangpt.presentation.screen.home.DrawerActions
import tr.com.ilangpt.presentation.screen.home.MainDrawerContent
import tr.com.ilangpt.presentation.theme.AppTheme

@Composable
fun App() {
  val authRepository: AuthRepository = getKoin().get()
  val authState by authRepository.authState.collectAsStateWithLifecycle()
  val user by authRepository.user.collectAsStateWithLifecycle()
  val navController = rememberNavController()
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  val backStackEntry by navController.currentBackStackEntryAsState()
  val drawerEnabled = backStackEntry?.destination?.hasRoute<HomeRoute>() == true
  var drawerActions by remember { mutableStateOf<DrawerActions?>(null) }

  fun closeDrawer() {
    scope.launch { drawerState.close() }
  }

  AppTheme {
    ModalNavigationDrawer(
      drawerState = drawerState,
      gesturesEnabled = drawerEnabled,
      drawerContent = {
        MainDrawerContent(
          onSettings = {
            navController.navigate(SettingsRoute)
            closeDrawer()
          },
          onProfile = {
            navController.navigate(ProfileRoute)
            closeDrawer()
          },
          historyQuery = drawerActions?.historyQuery?.value.orEmpty(),
          queryHistory = { drawerActions?.queryHistory(it) },
          filteredListingHistory = drawerActions?.filteredListingHistory?.value.orEmpty(),
          onHistoryItemClick = {
            drawerActions?.onHistoryItemClick(it)
            closeDrawer()
          },
          user = user,
        )
      },
    ) {
      RootNavGraph(
        authState = authState,
        navController = navController,
        drawerActions = { drawerActions = it },
        onOpenDrawer = { scope.launch { drawerState.open() } })
    }
  }
}
