package tr.com.ilangpt.data.datasource

//@OptIn(ExperimentalForeignApi::class)
//fun createDataStore(): DataStore<Preferences> {
//  return createDataStore {
//    val dir = NSFileManager.defaultManager.URLForDirectory(
//      directory = NSDocumentDirectory,
//      inDomain = NSUserDomainMask,
//      appropriateForURL = null,
//      create = false,
//      error = null
//    )
//    requireNotNull(dir).path + "/$DATA_STORE_FILE_NAME"
//  }
//}