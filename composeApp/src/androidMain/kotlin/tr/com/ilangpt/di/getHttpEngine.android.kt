package tr.com.ilangpt.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpEngine(): HttpClientEngine {
  return OkHttp.create()
}