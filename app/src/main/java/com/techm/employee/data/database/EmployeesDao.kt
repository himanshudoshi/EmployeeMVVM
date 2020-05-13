package com.techm.employee.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails

/** * The Data Access Object for the Employees class.
 * Defines methods for using the Employees class with Room.
 */
@Dao
interface EmployeesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employees: Employees): Long

    @Query("SELECT * FROM employees")
    fun getFacts(): LiveData<Employees>

    @Delete
    fun deleteUser(employee: Employees)
}