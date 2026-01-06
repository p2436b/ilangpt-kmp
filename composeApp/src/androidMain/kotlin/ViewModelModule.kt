import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import tr.com.ilangpt.screens.home.HomeViewModel

actual val viewModelModule = module {
  viewModelOf(::HomeViewModel)
}