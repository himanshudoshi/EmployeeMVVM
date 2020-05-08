package com.telstra.mvvmdemo.data.repository

import androidx.lifecycle.LiveData
import com.telstra.mvvmdemo.data.database.FactsDatabase
import com.telstra.mvvmdemo.data.model.Facts
import com.telstra.mvvmdemo.data.network.FactsApi
import com.telstra.mvvmdemo.data.network.SafeApiRequest

/**
 * Repository module for handling data operations.
 */
class FactsRepository(
    private val factsApi: FactsApi,
    private val factsDatabase: FactsDatabase
) : SafeApiRequest() {

    /** Fetch a list of [Facts]s from the Network. */
    suspend fun getFactsDataFromNetwork(): Facts {
        return apiRequest { factsApi.getData() }
    }

    /** Fetch a list of [Facts]s from the database. */
    fun getFactsFromDb(): LiveData<Facts> {
        return factsDatabase.getFactsDao().getFacts()
    }

    /** save a list of [Facts]s in the database. */
    suspend fun saveFacts(facts: Facts) {
        factsDatabase.getFactsDao().insert(facts)
    }
}
