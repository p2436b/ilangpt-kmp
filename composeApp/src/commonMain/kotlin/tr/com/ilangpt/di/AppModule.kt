package tr.com.ilangpt.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
  fun httpClient(engine: HttpClientEngine) = HttpClient(engine) {

    install(ContentNegotiation) {
      json(
        Json {
          ignoreUnknownKeys = true
          explicitNulls = false
        }
      )
    }

    defaultRequest {
      url("https://post-gpt-backend-bte3h3ftg3hgf2cu.westeurope-01.azurewebsites.net/")
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
    }
  }

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