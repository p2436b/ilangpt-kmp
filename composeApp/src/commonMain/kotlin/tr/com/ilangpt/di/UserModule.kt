package tr.com.ilangpt.di

import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import tr.com.ilangpt.data.repository.UserRepositoryImpl
import tr.com.ilangpt.domain.repository.UserRepository
import tr.com.ilangpt.presentation.screen.home.HomeViewModel

@Module(
  includes = [AppModule::class]
)
class UserModule {
  @Factory(binds = [UserRepository::class])
  fun userRepository(httpClient: HttpClient) = UserRepositoryImpl(httpClient)

  @KoinViewModel
  fun homeViewModel(userRepository: UserRepository) = HomeViewModel(userRepository)
}