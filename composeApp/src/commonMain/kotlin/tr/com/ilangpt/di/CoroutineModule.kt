package tr.com.ilangpt.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class CoroutineModule {

  @Factory
  fun applicationScope(): CoroutineScope =
    CoroutineScope(SupervisorJob() + Dispatchers.Default)
}