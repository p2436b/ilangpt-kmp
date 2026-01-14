package tr.com.ilangpt.domain.repository

import tr.com.ilangpt.network.dto.ListingDto

interface ListingRepository {
  suspend fun getListings(query: String): Result<List<ListingDto>>
}