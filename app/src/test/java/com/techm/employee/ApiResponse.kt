package com.techm.employee

import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails

class ApiResponse {
    companion object {
        fun getSampleResponse() : Employees {
            val status = "Success"
            val list : MutableList<EmployeesDetails> = mutableListOf()
            list.add(EmployeesDetails(id = "", employee_name = "", employee_salary = "543".toInt(),employee_age ="43".toInt() ,profile_image = ""))
            list.add(EmployeesDetails("", "", 323.toInt(),45.toInt(),""))
            list.add(EmployeesDetails("", "", 3235.toInt(),65.toInt(),""))
            return Employees(status,list)
        }
    }
}