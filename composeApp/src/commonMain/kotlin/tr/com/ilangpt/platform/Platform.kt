package tr.com.ilangpt.platform

enum class Platform { ANDROID, IOS, WEB, DESKTOP }

expect fun getPlatform(): Platform