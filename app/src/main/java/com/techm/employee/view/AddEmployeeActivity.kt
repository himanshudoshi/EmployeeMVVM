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
 * Activity that Add Employee Details
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
        etAge = findViewById(R.id.etAge)
        etSalary = findViewById(R.id.etSalary)
        btnRegister = findViewById<Button>(R.id.btnRegister)
        setUpUi()
        initViewModel()
        btnRegister.setOnClickListener {
            validation()
            initViewModel()
          // val myHandler = Handler()
          //  val myRun = Runnable {
           //     etName.text.clear()
           //     etAge.text.clear()
            //    etSalary.text.clear()
          //  }
           // myHandler.postDelayed(myRun, 2000);
        }
    }

    /** setUp UI */
    private fun setUpUi() {
        employeesApi = EmployeesApi()
        database = EmployeesDatabase(this)
        employeesRepository = EmployeesRepository(employeesApi, database)
        factory = EmployeesViewModelFactory(employeesRepository)
    }

    /** Initialize ViewModel,check connectivity and send data from viewModel to Activity. */
    private fun initViewModel() {
        employeesViewModel = ViewModelProvider(this, factory).get(EmployeesViewModel::class.java)
        // show data from Viewmodel to UI
        when {
            NetworkConnection().checkNetworkAvailability(applicationContext) -> {
                employeesViewModel.addEmployee()
                Toast.makeText(
                    applicationContext,
                    getString(R.string.addemployeedetails),
                    Toast.LENGTH_SHORT
                )?.show()
            }
            else -> {
                toast(getString(R.string.noconnectivity))
            }
        }
    }

    /** EditTexts Validations */
    private fun validation() {
        val name = etName.text.toString()
        val age = etAge.text.toString()
        val salary = etSalary.text.toString()
        when {
            name == "" -> {
                etName.error = getString(R.string.validatename)
            }
            age == "" -> {
                etAge.error = getString(R.string.validateage)
            }
            salary == "" -> {
                etSalary.error = getString(R.string.validatesalary)
            }

        }
    }
}