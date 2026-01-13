package tr.com.ilangpt.domain.model

data class Listing(
  val id: Int,
  val title: String,
  val url: String,
  val coverImage: String,
  val websiteName: String,
  val city: String,
  val district: String,
  val neighborhood: String,
  val listingType: String,
  val categoryPath: String,
  val score: Double
)