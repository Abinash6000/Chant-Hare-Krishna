package com.project.chantharekrishna

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val MERCHANT_DATASTORE = "merchant_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MERCHANT_DATASTORE)

class DataStoreManager(val context: Context) {

    companion object {
        val MALA_KEY = intPreferencesKey("MALA_KEY")
        val MANTRA_KEY = intPreferencesKey("MANTRA_KEY")
        val DATE_KEY = stringPreferencesKey("DATE_KEY")
    }

    suspend fun saveToDataStore(malaCount: Int, mantraCount: Int, date: String) {
        context.dataStore.edit {
            it[MALA_KEY] = malaCount
            it[MANTRA_KEY] = mantraCount
            it[DATE_KEY] = date
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        Triple(it[MALA_KEY] ?: 0, it[MANTRA_KEY] ?: 0, it[DATE_KEY] ?: "")
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }

}