package tr.com.ilangpt.data.datasource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object DataStoreFileProvider {
  fun preferencesFilePath(): String
}