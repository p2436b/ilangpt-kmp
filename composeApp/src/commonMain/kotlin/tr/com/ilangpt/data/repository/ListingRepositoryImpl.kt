package tr.com.ilangpt.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tr.com.ilangpt.domain.repository.ListingRepository
import tr.com.ilangpt.network.dto.ListingDto

class ListingRepositoryImpl(private val httpClient: HttpClient) : ListingRepository {
  override suspend fun getListings(query: String): Result<List<ListingDto>> {
    return withContext(Dispatchers.IO) {
      runCatching {

        httpClient
          .get("api/listing/search") {
            parameter("query", query)
            parameter("topK", 5)
          }
          .body()
      }
    }
  }
}