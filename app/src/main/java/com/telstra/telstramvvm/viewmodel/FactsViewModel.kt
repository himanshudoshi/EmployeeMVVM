package com.telstra.telstramvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.telstra.telstramvvm.data.model.Facts
import com.telstra.telstramvvm.data.repository.FactsRepository
import com.telstra.telstramvvm.utils.ApiException
import com.telstra.telstramvvm.utils.Coroutines
import com.telstra.telstramvvm.utils.NoInternetException
/**
 *  Fetch fact list from a repository
 */
class FactsViewModel(private val factsRepository: FactsRepository) : ViewModel() {

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

    fun getFactsFromDb(): LiveData<Facts> =

        factsRepository.getFactsFromDb()
}