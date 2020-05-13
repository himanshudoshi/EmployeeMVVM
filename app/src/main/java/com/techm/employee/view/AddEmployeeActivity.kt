package com.techm.employee.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techm.employee.R
import com.techm.employee.data.database.EmployeesDatabase
import com.techm.employee.data.model.EmployeesDetails
import com.techm.employee.data.network.EmployeesApi
import com.techm.employee.data.repository.EmployeesRepository
import com.techm.employee.utils.NetworkConnection
import com.techm.employee.utils.hide
import com.techm.employee.utils.toast
import com.techm.employee.viewmodel.EmployeesViewModel
import com.techm.employee.viewmodel.EmployeesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

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
        //subscribeObserver()
        btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            // subscribeObserver()
            //initViewModel()
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

    /** Subscribe the observers & Load Employee details in UI. */
    private fun subscribeObserver() {

        employeesViewModel.getFactsFromDb().observe(this, Observer { it ->

            it?.let { it ->
                //supportActionBar?.title = it.title
                it.data?.let {

                }
            }
        })
    }

    private fun register() {
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
            else -> {
                val empAge = age.toInt()
                val empSalary = salary.toFloat()
            }
        }
    }
}