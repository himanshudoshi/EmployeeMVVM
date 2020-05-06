package com.telstra.telstramvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.telstra.telstramvvm.data.repository.FactsRepository

class FactsViewModelFactory(private val factsRepository: FactsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return FactsViewModel(factsRepository) as T
    }
}