package com.techm.employee.data.model

import com.google.gson.annotations.SerializedName

/**
 * data class for EmployeesDetails
 */

data class EmployeesDetails(
    @SerializedName("id") val id: String,
    @SerializedName("employee_name") val employee_name: String,
    @SerializedName("employee_salary") val employee_salary: Int,
    @SerializedName("employee_age") val employee_age: Int,
    @SerializedName("profile_image") val profile_image: String
) {


}