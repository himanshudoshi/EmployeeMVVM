package com.telstra.mvvmdemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.telstra.mvvmdemo.data.model.Facts
import com.telstra.mvvmdemo.data.repository.FactsRepository
import com.telstra.mvvmdemo.utils.ApiException
import com.telstra.mvvmdemo.utils.Coroutines
import com.telstra.mvvmdemo.utils.NoInternetException

/**
 *  The [ViewModel] for fetching a list of [Facts]s.
 */
class FactsViewModel(private val factsRepository: FactsRepository) : ViewModel() {

    /** Fetch List of [Facts]s from Network using Coroutines. */
    fun saveFacts() {
        Coroutines.main {
            val factsResponse: Facts = factsRepository.getFactsDataFromNetwork()
            try {
                factsResponse.let {
                    factsRepository.saveFacts(it)
                }

            } catch (e: ApiException) {
                Log.e("Exception", "Exception" + e.printStackTrace())

            } catch (e: NoInternetException) {
                Log.e("Internet Exception", "No Internet Exception" + e.printStackTrace())
            }
        }
    }

    /** Fetch List list of [Facts]s from database. */
    fun getFactsFromDb(): LiveData<Facts> =
        factsRepository.getFactsFromDb()
}