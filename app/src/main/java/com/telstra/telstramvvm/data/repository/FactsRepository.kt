package com.telstra.telstramvvm.data.repository

import androidx.lifecycle.LiveData
import com.telstra.telstramvvm.data.db.FactsDatabase
import com.telstra.telstramvvm.data.model.Facts
import com.telstra.telstramvvm.data.network.FactsApi
import com.telstra.telstramvvm.data.network.SafeApiRequest

class FactsRepository(
    private val factsApi: FactsApi,
    private val factsDatabase: FactsDatabase
) : SafeApiRequest() {

    suspend fun getFactsDataFromNetwork(): Facts {//LiveData<News> {

        return apiRequest { factsApi.getData() }
    }

    fun getFactsFromDb(): LiveData<Facts> {

        return factsDatabase.getFactsDao().getFacts()
    }

    suspend fun saveFacts(facts: Facts) {

        factsDatabase.getFactsDao().insert(facts)
    }
}
