package com.techm.employee.adapter

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.techm.employee.data.model.EmployeesDetails
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * EmployeesAdapter Test class check item counts and item position in Recyclerview
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class EmployeesAdapterTest {

    private lateinit var employeesAdapter: EmployeesAdapter
    val one = EmployeesDetails("", "", 323.toInt(), 45.toInt(), "")
    val two = EmployeesDetails("", "", 223.toInt(), 55.toInt(), "")

    /** SetUp Employee Adapter */
    @Before
    fun setUp() {
        employeesAdapter = EmployeesAdapter(ApplicationProvider.getApplicationContext())
    }

    /** Function to Check Item counts */
    @Test
    fun checkItemCount() {
        Assert.assertTrue(employeesAdapter.itemCount == 3)
    }

    /** Function to Check Item at particular position */
    @Test
    fun checkItemAtPosition() {
        val sample = EmployeesDetails("one", "one", 323, 45, "")
        assertEquals(sample, one)
        Assert.assertNotEquals(sample, two)
    }
}