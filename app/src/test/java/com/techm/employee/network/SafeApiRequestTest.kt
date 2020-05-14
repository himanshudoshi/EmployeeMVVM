package com.techm.employee.network

import com.techm.employee.ApiResponse
import com.techm.employee.data.model.Employees
import com.techm.employee.data.network.EmployeesApi
import com.techm.employee.data.network.SafeApiRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/** Api Request Test */
class SafeApiRequestTest {
    private lateinit var employeesApi: EmployeesApi
    private lateinit var safeApiRequest: SafeApiRequest

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        safeApiRequest = mockk()
        employeesApi = mockk()
    }
/**  ApiRequest call and response returned */
    @Test
    fun `GIVEN safe api request WHEN request call api THEN api response returned`() {
        // Given
        val sampleMock = mockk<suspend () -> Response<Employees>>()
        coEvery { safeApiRequest.apiRequest(sampleMock) }.answers { ApiResponse.getSampleResponse() }
        // When
        runBlocking { safeApiRequest.apiRequest(sampleMock)
        }
        // Then
        coVerify { safeApiRequest.apiRequest(sampleMock) }
    }
}