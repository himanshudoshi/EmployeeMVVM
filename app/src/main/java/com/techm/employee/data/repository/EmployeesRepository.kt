package com.techm.employee.data.repository

import androidx.lifecycle.LiveData
import com.techm.employee.data.database.EmployeesDatabase
import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails
import com.techm.employee.data.network.EmployeesApi
import com.techm.employee.data.network.SafeApiRequest

/**
 * Repository module for handling data operations.
 */
class EmployeesRepository(
    private val employeesApi: EmployeesApi,
    private val employeesDatabase: EmployeesDatabase
) : SafeApiRequest() {

    /** Fetch a list of Employees from the Network. */
    suspend fun getFactsDataFromNetwork(): Employees {
        return apiRequest { employeesApi.getData() }
    }

    /** Fetch a list of Employees from the database. */
    fun getFactsFromDb(): LiveData<Employees> {
        return employeesDatabase.getEmployeesDao().getFacts()
    }

    /** save a list of Employees in the database. */
    fun saveEmployees(employees: Employees) {
        employeesDatabase.getEmployeesDao().insert(employees)
    }

    /** Fetch a list of Employees from the Network. */
    suspend fun createEmployeeToNetwork(name: String, salary: Int, age: Int): EmployeesDetails {
        return apiRequest { employeesApi.createUser(name, salary, age) }
    }

}
