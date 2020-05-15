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

/**
 *  The ViewModel for fetching a list of Employees.
 */
class EmployeesViewModel(private val employeesRepository: EmployeesRepository) : ViewModel() {

    private  var name: String = "test"
    private var age =23
    private var salary =123
    private var id =2

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

    /** Add Employee to the Network */
    fun addEmployee() {
        Coroutines.main {
            val addemployeeResponse: EmployeesDetails =
                employeesRepository.createEmployeeToNetwork(name,salary,age)
            try {
                addemployeeResponse.let {
                    Log.e(
                        "Add Employee",
                        "Add Employee$addemployeeResponse"
                    )
                    //employeesRepository.saveEmployees(it)
                }

            } catch (e: ApiException) {
                Log.e("Exception", "Exception" + e.printStackTrace())

            } catch (e: NoInternetException) {
                Log.e("Internet Exception", "No Internet Exception" + e.printStackTrace())
            }
        }
    }

    /** Delete Employee from the Network by Employe Id */
    fun deleteEmployee(removeAt: Unit) {
        Coroutines.main {
            val deleteEmployeeResponse =employeesRepository.DeleteEmployeeToNetwork(id)
            try {
                deleteEmployeeResponse.let {
                    Log.e(
                        "Delete Employee",
                        "Delete Employee"+ deleteEmployeeResponse.message
                    )
                }
            } catch (e: ApiException) {
                Log.e("Exception", "Exception" + e.printStackTrace())

            } catch (e: NoInternetException) {
                Log.e("Internet Exception", "No Internet Exception" + e.printStackTrace())
            }
        }
    }
}

