package tr.com.ilangpt.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(): DataStore<Preferences> {
  return PreferenceDataStoreFactory.createWithPath(
    produceFile = { DataStoreFileProvider.preferencesFilePath().toPath() }
  )
}