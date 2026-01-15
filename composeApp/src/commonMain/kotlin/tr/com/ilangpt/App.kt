package tr.com.ilangpt

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import tr.com.ilangpt.domain.repository.AuthRepository
import tr.com.ilangpt.presentation.navigation.ProfileRoute
import tr.com.ilangpt.presentation.navigation.RootNavGraph
import tr.com.ilangpt.presentation.navigation.SettingsRoute
import tr.com.ilangpt.presentation.screen.home.MainDrawerContent
import tr.com.ilangpt.presentation.theme.AppTheme

@Composable
fun App() {
  val authRepository: AuthRepository = getKoin().get()
  val authState by authRepository.authState.collectAsState()
  val user by authRepository.user.collectAsState()
  val navController = rememberNavController()
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  AppTheme {
    ModalNavigationDrawer(
      drawerState = drawerState,
      drawerContent = {
        MainDrawerContent(
          onSettings = {
            navController.navigate(SettingsRoute)
            scope.launch {
              drawerState.close()
            }
          },
          onProfile = {
            navController.navigate(ProfileRoute)
            scope.launch {
              drawerState.close()
            }
          },
          historyQuery = "",
          queryHistory = { /*viewModel.queryHistory(it)*/ },
          filteredListingHistory = emptyList(),
          onHistoryItemClick = {
            scope.launch {
              drawerState.close()
            }
          },
          user = user,
        )
      },
    ) {
      RootNavGraph(authState, navController)
    }
  }
}
