package com.techm.employee.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techm.employee.R
import com.techm.employee.adapter.EmployeesAdapter
import com.techm.employee.data.database.EmployeesDatabase
import com.techm.employee.data.model.EmployeesDetails
import com.techm.employee.data.network.EmployeesApi
import com.techm.employee.data.repository.EmployeesRepository
import com.techm.employee.utils.*
import com.techm.employee.viewmodel.EmployeesViewModel
import com.techm.employee.viewmodel.EmployeesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity that displays all Employee details in Recyclerview in the app
 */
class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var employeesViewModel: EmployeesViewModel
    private lateinit var employeesApi: EmployeesApi
    private lateinit var database: EmployeesDatabase
    private lateinit var employeesRepository: EmployeesRepository
    private lateinit var factory: EmployeesViewModelFactory
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var employeesAdapter: EmployeesAdapter
    private val mArrayList: ArrayList<EmployeesDetails>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
        initViewModel()
        initRecyclerView()
        onRefresh()
        swipeToDelete()
        subscribeObserver()
    }

    /** setUp UI */
    private fun setUpUi() {
        progressBar.show()
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
                employeesViewModel.saveEmployees()
            }
            else -> {
                toast(getString(R.string.noconnectivity))
                progressBar.hide()
            }
        }
    }

    /** Initialize RecyclerView to display data in screen. */
    private fun initRecyclerView() {
        linearLayout = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayout
        employeesAdapter = EmployeesAdapter(context = this)
        recycler_view.adapter = employeesAdapter
    }

    /** Pull To Refresh functionality to fetch latest data. */
    private fun onRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            employeesViewModel.saveEmployees()
        }
    }

    /** SwipeToDelete Functionality to remove selected item from RecyclerView */
    private fun swipeToDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recycler_view.adapter as EmployeesAdapter
                (recycler_view.adapter as EmployeesAdapter).removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    /** Subscribe the observers & Load Employee details in UI. */
    private fun subscribeObserver() {

        employeesViewModel.getEmployeesFromDatabase().observe(this, Observer { it ->

            it?.let { it ->
                //supportActionBar?.title = it.title
                it.data?.let {
                    employeesAdapter.setList(it as ArrayList<EmployeesDetails>)
                    progressBar.hide()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    /**
     * Created Option Menu and added ADD and Search Items
     * Added SearchView Functionality to Search Specific Item in RecyclerView
     **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val search = menu!!.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //  employeesAdapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
              /*  if (TextUtils.isEmpty(newText))
                    employeesAdapter.notifyDataSetChanged()*/
                employeesAdapter.filter?.filter(newText)
                Log.e("newText", "newText" + newText)
                // employeesAdapter.notifyDataSetChanged()
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_user -> {
                val intent = Intent(this, AddEmployeeActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}





