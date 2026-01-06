package tr.com.ilangpt

import KoinInitializer
import android.app.Application

class TheApp: Application() {
  override fun onCreate() {
    super.onCreate()
    KoinInitializer(applicationContext).init()
  }
}