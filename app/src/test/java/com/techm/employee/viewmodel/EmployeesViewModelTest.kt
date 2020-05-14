package com.techm.employee.viewmodel

import com.techm.employee.data.repository.EmployeesRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/** EmployeeViewModel to test Business Logic */
class EmployeesViewModelTest {

    @RelaxedMockK
    private lateinit var repository: EmployeesRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    /** Send Api Request and check Response */
    @Test
    fun `GIVEN viewmodel WHEN Employees api request THEN Employee Detail field should have values`() {
        runBlocking {
            launch(Dispatchers.Main) {
                // Given
                val viewmodel = EmployeesViewModel(repository)
                // WHEN
                val result = viewmodel.saveEmployees()
                // Then
                assertNotNull(result)
            }
        }
    }
}
