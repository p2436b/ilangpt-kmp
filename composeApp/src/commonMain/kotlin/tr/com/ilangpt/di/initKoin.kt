package tr.com.ilangpt.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.core.module.Module

fun initKoin(
  appModules: List<Module>,
  config: KoinAppDeclaration? = null
) {
  startKoin {
    config?.invoke(this)
    modules(appModules)
  }
}