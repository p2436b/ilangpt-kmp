package tr.com.ilangpt

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import tr.com.ilangpt.data.datasource.createDataStore

fun MainViewController() = ComposeUIViewController {
  App(
    prefs = remember { createDataStore() }
  )
}