package tr.com.ilangpt.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import tr.com.ilangpt.network.dto.ListingDto

class ListingApi(
  private val client: HttpClient,
  private val baseUrl: String
) {
  suspend fun searchListings(query: String, topK: Int = 5): List<ListingDto> {
    return client
      .get("$baseUrl/api/listing/search") {
        parameter("query", query)
        parameter("topK", topK)
      }
      .body()
  }

  suspend fun getListing(id: Long): ListingDto {
    return client
      .get("$baseUrl/api/listing/$id")
      .body()
  }
}
