package tr.com.ilangpt

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import tr.com.ilangpt.database.getAppDatabase

fun MainViewController() = ComposeUIViewController {

  val appDatabase = remember {
    getAppDatabase()
  }

  App(appDatabase)
}
