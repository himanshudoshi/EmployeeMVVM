package com.techm.employee.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.techm.employee.R
import com.techm.employee.data.database.EmployeesDatabase
import com.techm.employee.data.network.EmployeesApi
import com.techm.employee.data.repository.EmployeesRepository
import com.techm.employee.utils.NetworkConnection
import com.techm.employee.utils.toast
import com.techm.employee.viewmodel.EmployeesViewModel
import com.techm.employee.viewmodel.EmployeesViewModelFactory

/**
 * Activity that Create Employee
 */
class AddEmployeeActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etSalary: EditText
    private lateinit var employeesViewModel: EmployeesViewModel
    private lateinit var employeesApi: EmployeesApi
    private lateinit var database: EmployeesDatabase
    private lateinit var employeesRepository: EmployeesRepository
    private lateinit var factory: EmployeesViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etName)
        etSalary = findViewById(R.id.etSalary)
        setUpUi()
        initViewModel()
        btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            validation()
            initViewModel()
            Toast.makeText(
                applicationContext,
                getString(R.string.addemployeedetails),
                Toast.LENGTH_SHORT
            )?.show()
        }
    }

    /** setUp UI */
    private fun setUpUi() {
        employeesApi = EmployeesApi()
        database = EmployeesDatabase(this)
        employeesRepository = EmployeesRepository(employeesApi, database)
        factory = EmployeesViewModelFactory(employeesRepository)
    }

    private fun initViewModel() {
        employeesViewModel = ViewModelProvider(this, factory).get(EmployeesViewModel::class.java)
        // show data from Viewmodel to UI
        when {
            NetworkConnection().checkNetworkAvailability(applicationContext) -> {
                employeesViewModel.addEmployee()
            }
            else -> {
                toast(getString(R.string.noconnectivity))
                //progressBar.hide()
            }
        }
    }

    private fun validation() {
        val name = etName.text.toString()
        val age = etAge.text.toString()
        val salary = etSalary.text.toString()
        when {
            name == "" -> {
                etName.error = "Enter name"
            }
            age == "" -> {
                etAge.error = "Enter age"
            }
            salary == "" -> {
                etSalary.error = "Enter salary"
            }
            /*else -> {
                val empAge = age.toInt()
                val empSalary = salary.toFloat()
            }*/
        }
    }
}