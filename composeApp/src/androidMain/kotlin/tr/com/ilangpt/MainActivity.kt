package tr.com.ilangpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tr.com.ilangpt.data.datasource.appContext
import tr.com.ilangpt.database.getAppDatabase

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    appContext = applicationContext

    val appDatabase = getAppDatabase(applicationContext)

    setContent {
      App(appDatabase)
    }
  }
}