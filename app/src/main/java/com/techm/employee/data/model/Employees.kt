package com.techm.employee.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * data class for Employees
 * Entity Class for Room Database
 */
const val PRIMARY_KEY = 0

@Entity
data class Employees(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("data")
    val data: List<EmployeesDetails>
) {

    @PrimaryKey(autoGenerate = true)
    var id = PRIMARY_KEY

    companion object {
        @Ignore
        val messageOptionListType: Type = object : TypeToken<List<EmployeesDetails>>() {}.type
    }
}
