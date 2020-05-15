package com.techm.employee.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails
import com.techm.employee.data.repository.EmployeesRepository
import com.techm.employee.utils.ApiException
import com.techm.employee.utils.Coroutines
import com.techm.employee.utils.NoInternetException
import kotlin.properties.Delegates

/**
 *  The ViewModel for fetching a list of Employees.
 */
class EmployeesViewModel(private val employeesRepository: EmployeesRepository) : ViewModel() {

    private lateinit var name: String
    private var age by Delegates.notNull<Int>()
    private var salary by Delegates.notNull<Int>()

    /** Fetch List of Employees from Network using Coroutines. */
    fun saveEmployees() {
        Coroutines.main {
            val employeeResponse: Employees = employeesRepository.getFactsDataFromNetwork()
            try {
                employeeResponse.let {
                    employeesRepository.saveEmployees(it)
                }

            } catch (e: ApiException) {
                Log.e("Exception", "Exception" + e.printStackTrace())

            } catch (e: NoInternetException) {
                Log.e("Internet Exception", "No Internet Exception" + e.printStackTrace())
            }
        }
    }

    /** Fetch List of Employees from database. */
    fun getEmployeesFromDatabase(): LiveData<Employees> =
        employeesRepository.getEmployeesFromDatabase()

    /** Add List of Employee. */
    fun addEmployee() {
        Coroutines.main {
            val addemployeeResponse: EmployeesDetails =
                employeesRepository.createEmployeeToNetwork("test", 123, 23)
            try {
                addemployeeResponse.let {
                    //employeesRepository.saveEmployees(it)
                }

            } catch (e: ApiException) {
                Log.e("Exception", "Exception" + e.printStackTrace())

            } catch (e: NoInternetException) {
                Log.e("Internet Exception", "No Internet Exception" + e.printStackTrace())
            }
        }
    }
}

