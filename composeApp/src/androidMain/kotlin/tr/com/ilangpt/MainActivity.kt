package tr.com.ilangpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.koin.androidContext
import tr.com.ilangpt.data.datasource.appContext
import tr.com.ilangpt.database.createDatabase
import tr.com.ilangpt.database.getDatabaseBuilder
import org.koin.core.context.startKoin
import tr.com.ilangpt.di.appModule
import tr.com.ilangpt.di.userModule
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    appContext = applicationContext

    startKoin {
      androidContext(applicationContext)
      modules(   appModules)

    }

    val database = createDatabase(getDatabaseBuilder(applicationContext))

    setContent {
      App(database)
    }
  }
}