package tr.com.ilangpt.di

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import tr.com.ilangpt.data.repository.AuthRepositoryImpl
import tr.com.ilangpt.data.repository.ListingRepositoryImpl
import tr.com.ilangpt.domain.repository.AuthRepository
import tr.com.ilangpt.domain.repository.ListingRepository
import tr.com.ilangpt.domain.repository.PreferencesRepository
import tr.com.ilangpt.presentation.screen.home.HomeViewModel
import tr.com.ilangpt.presentation.screen.profile.ProfileViewModel
import tr.com.ilangpt.presentation.screen.settings.SettingsViewModel
import tr.com.ilangpt.presentation.screen.signIn.SignInViewModel

@Module(
  includes = [AppModule::class, CoroutineModule::class]
)
class UserModule {
  @Single(binds = [AuthRepository::class])
  fun authRepository(
    httpClient: HttpClient,
    prefsRepository: PreferencesRepository,
    coroutineScope: CoroutineScope
  ): AuthRepository =
    AuthRepositoryImpl(httpClient, prefsRepository, coroutineScope)


  @Single(binds = [ListingRepository::class])
  fun listingRepository(httpClient: HttpClient) = ListingRepositoryImpl(httpClient)

  @KoinViewModel
  fun homeViewModel(authRepository: AuthRepository, listingRepository: ListingRepository) =
    HomeViewModel(authRepository, listingRepository)

  @KoinViewModel
  fun signViewModel(authRepository: AuthRepository) = SignInViewModel(authRepository)

  @KoinViewModel
  fun settingsViewModel(authRepository: AuthRepository) = SettingsViewModel(authRepository)

  @KoinViewModel
  fun profileViewModel(authRepository: AuthRepository) = ProfileViewModel(authRepository)

}