package tr.com.ilangpt

import KoinInitializer
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import createDataStore


fun MainViewController() = ComposeUIViewController(configure = {
  KoinInitializer().init()
}) {
  App(
    prefs = remember { createDataStore() }
  )
}