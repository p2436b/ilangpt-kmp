package tr.com.ilangpt.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpEngine(): HttpClientEngine {
  return Darwin.create()
}