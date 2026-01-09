package tr.com.ilangpt

import android.app.Application
import org.koin.android.ext.koin.androidContext
import tr.com.ilangpt.di.initKoin

class TheApp : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin {
      androidContext(this@TheApp)
    }
  }
}