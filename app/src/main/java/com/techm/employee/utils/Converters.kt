package com.techm.employee.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails

/**
 * converter class to store the custom type of objects in Database.
 */
class Converters {

    private val gson = Gson()

    @TypeConverter
    fun convertTagsListToDatabase(tags: List<EmployeesDetails>?): String? =
        if (tags != null) gson.toJson(tags) else null

    @TypeConverter
    fun convertDbToTags(value: String?): List<EmployeesDetails> {
        if (value != null) {
            return gson.fromJson(value, Employees.messageOptionListType)
        }
        return listOf()
    }
}