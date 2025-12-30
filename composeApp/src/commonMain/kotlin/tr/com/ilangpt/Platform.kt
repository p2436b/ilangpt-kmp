package tr.com.ilangpt

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform