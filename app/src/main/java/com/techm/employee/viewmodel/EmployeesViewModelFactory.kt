package com.techm.employee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techm.employee.data.repository.EmployeesRepository

/**
 * The ViewModelFactory class to create instance for ViewModel
 */
@Suppress("UNCHECKED_CAST")
class EmployeesViewModelFactory(private val employeesRepository: EmployeesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        EmployeesViewModel(employeesRepository) as T
}