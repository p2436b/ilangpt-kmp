package tr.com.ilangpt

import KoinInitializer
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(configure = {
  KoinInitializer().init()
}) {
  App()
}