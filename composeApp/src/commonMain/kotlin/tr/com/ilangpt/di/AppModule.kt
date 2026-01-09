package tr.com.ilangpt.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import tr.com.ilangpt.AppViewModel
import tr.com.ilangpt.data.datasource.PreferencesDataSource
import tr.com.ilangpt.data.datasource.createDataStore
import tr.com.ilangpt.data.repository.PreferencesRepositoryImpl
import tr.com.ilangpt.domain.repository.PreferencesRepository


@Module
class AppModule {
  @Single
  fun httpClient(engine: HttpClientEngine) = HttpClient(engine)

  @Single
  fun dataSource(engine: HttpClientEngine) = createDataStore()

  @Single
  fun preferencesDataSource(dataStore: DataStore<Preferences>) = PreferencesDataSource(dataStore)

  @Factory(binds = [PreferencesRepository::class])
  fun preferencesRepository(dataStore: PreferencesDataSource) = PreferencesRepositoryImpl(dataStore)

  @KoinViewModel
  fun appViewModel(preferencesRepository: PreferencesRepository) =
    AppViewModel(preferencesRepository)

  @Factory
  fun engine() = getHttpEngine()
}