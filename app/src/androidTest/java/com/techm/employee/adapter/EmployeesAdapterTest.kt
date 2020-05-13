package com.techm.employee.adapter

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
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

    //val one = EmployeesDetails("one", "one", one)
    // val two = FactsItem("two", "two", "two")
    // val three = FactsItem("three", "three", "three")

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
        //  val sample = FactsItem("one", "one", "one")
        //  assertEquals(sample, one)
        // Assert.assertNotEquals(sample, two)
    }
}