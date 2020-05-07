package com.telstra.telstramvvm.data.repository

import androidx.lifecycle.LiveData
import com.telstra.telstramvvm.data.db.FactsDatabase
import com.telstra.telstramvvm.data.model.Facts
import com.telstra.telstramvvm.data.network.FactsApi
import com.telstra.telstramvvm.data.network.SafeApiRequest

/**
 * Class to fetch Fact data from API
 */
class FactsRepository(
    private val factsApi: FactsApi,
    private val factsDatabase: FactsDatabase
) : SafeApiRequest() {
    /**
     *  fetch data from Network
     */
    suspend fun getFactsDataFromNetwork(): Facts {

        return apiRequest { factsApi.getData() }
    }
    /**
     *  fetch data from DB
     */
    fun getFactsFromDb(): LiveData<Facts> {

        return factsDatabase.getFactsDao().getFacts()
    }

    suspend fun saveFacts(facts: Facts) {

        factsDatabase.getFactsDao().insert(facts)
    }
}
