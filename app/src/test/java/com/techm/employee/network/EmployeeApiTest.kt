package com.techm.employee.network

import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails
import com.techm.employee.data.network.EmployeesApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class EmployeeApiTest {

    private lateinit var employeesApi: EmployeesApi

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        employeesApi = mockk()
    }

    @Test
    fun `GIVEN interface WHEN calling Employees detail api THEN Employees response`() {
        // Given
        coEvery{ employeesApi.getData()} returns getData()

        // When
        runBlocking { employeesApi.getData() }

        // Then
        coVerify { employeesApi.getData() }
    }

    fun getData() : Response<Employees> {
        val status = "success"
        val list : MutableList<EmployeesDetails> = mutableListOf()
        list.add(EmployeesDetails("1", "Himanshu", 7654,34,""))
        return Response.success(Employees(status,list))
    }

}


