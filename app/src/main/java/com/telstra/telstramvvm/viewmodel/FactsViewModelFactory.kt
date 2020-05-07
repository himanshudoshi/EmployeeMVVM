package com.telstra.telstramvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.telstra.telstramvvm.data.repository.FactsRepository

/**
 * To Pass factsrepository to Viewmodels constructor
 */
@Suppress("UNCHECKED_CAST")
class FactsViewModelFactory(private val factsRepository: FactsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FactsViewModel(factsRepository) as T
}