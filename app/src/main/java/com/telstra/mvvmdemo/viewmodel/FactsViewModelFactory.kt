package com.telstra.mvvmdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.telstra.mvvmdemo.data.repository.FactsRepository

/**
 * The ViewModelFactory class to create instance for ViewModel
 */
@Suppress("UNCHECKED_CAST")
class FactsViewModelFactory(private val factsRepository: FactsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FactsViewModel(factsRepository) as T
}