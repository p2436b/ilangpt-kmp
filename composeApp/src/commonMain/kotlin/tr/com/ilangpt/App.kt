package tr.com.ilangpt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.mp.KoinPlatform.getKoin
import tr.com.ilangpt.domain.repository.AuthRepository
import tr.com.ilangpt.presentation.navigation.RootNavGraph
import tr.com.ilangpt.presentation.theme.AppTheme

@Composable
fun App() {
  val authRepository: AuthRepository = getKoin().get()
  val authState by authRepository.authState.collectAsState()

  AppTheme {
    RootNavGraph(authState)
  }
}