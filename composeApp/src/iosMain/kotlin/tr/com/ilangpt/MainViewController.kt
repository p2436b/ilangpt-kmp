package tr.com.ilangpt

import androidx.compose.ui.window.ComposeUIViewController
import tr.com.ilangpt.database.createDatabase
import tr.com.ilangpt.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {

  val database = createDatabase(getDatabaseBuilder())

  App(database)
}
