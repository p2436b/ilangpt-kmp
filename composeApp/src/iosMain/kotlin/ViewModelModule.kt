import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tr.com.ilangpt.screens.home.HomeViewModel

actual val viewModelModule = module {
  singleOf(::HomeViewModel)
}