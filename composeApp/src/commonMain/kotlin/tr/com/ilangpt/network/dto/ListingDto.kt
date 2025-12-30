package tr.com.ilangpt.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListingDto(
  val id: Long,
  val url: String,
  val title: String,
  val coverImage: String,
  val websiteName: String,
  val city: String,
  val district: String,
  val neighborhood: String,
  val listingType: String,
  val categoryPath: String,
  val score: Double,
)