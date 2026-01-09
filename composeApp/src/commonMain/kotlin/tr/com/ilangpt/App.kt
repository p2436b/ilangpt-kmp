package tr.com.ilangpt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import tr.com.ilangpt.presentation.navigation.RootNavGraph
import tr.com.ilangpt.presentation.theme.AppTheme

@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel()) {

  val authState by viewModel.authState.collectAsState()

  AppTheme {
    RootNavGraph(authState)
  }
}