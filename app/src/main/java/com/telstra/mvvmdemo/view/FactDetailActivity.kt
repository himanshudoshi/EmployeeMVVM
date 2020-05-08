package com.telstra.mvvmdemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.mvvmdemo.R
import com.telstra.mvvmdemo.adapter.FactsAdapter
import com.telstra.mvvmdemo.data.database.FactsDatabase
import com.telstra.mvvmdemo.data.model.FactsItem
import com.telstra.mvvmdemo.data.network.FactsApi
import com.telstra.mvvmdemo.data.repository.FactsRepository
import com.telstra.mvvmdemo.utils.*
import com.telstra.mvvmdemo.viewmodel.FactsViewModel
import com.telstra.mvvmdemo.viewmodel.FactsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity that displays all facts details in Recyclerview in the app
 */
class FactsDetailActivity : AppCompatActivity() {

    private lateinit var factsViewModel: FactsViewModel
    private lateinit var api: FactsApi
    private lateinit var db: FactsDatabase
    private lateinit var factsRepository: FactsRepository
    private lateinit var factory: FactsViewModelFactory
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var factsAdapter: FactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
        initViewModel()
        initRecyclerView()
        onRefresh()
        subscribeObserver()
    }

    /**
     * setUp UI
     */
    private fun setUpUi() {
        progressBar.show()
        api = FactsApi()
        db = FactsDatabase(this)
        factsRepository = FactsRepository(api, db)
        factory = FactsViewModelFactory(factsRepository)
    }

    /**
     * Initialize ViewModel,check connectivity and send data from viewModel to Activity
     */
    private fun initViewModel() {
        factsViewModel = ViewModelProvider(this, factory).get(FactsViewModel::class.java)
        // show data from Viewmodel to UI
        when {
            NetworkConnection().checkNetworkAvailability(applicationContext) -> {
                factsViewModel.saveFacts()
            }
            else -> {
                toast(getString(R.string.noconnectivity))
                progressBar.hide()
            }
        }
    }

    /**
     * Initialize RecyclerView to display data in screen
     */
    private fun initRecyclerView() {
        linearLayout = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayout
        factsAdapter = FactsAdapter(context = this)
        recycler_view.adapter = factsAdapter
    }

    /**
     * Pull To Refresh functionality to fetch latest data
     */
    private fun onRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            factsViewModel.saveFacts()
        }
    }

    /**
     * Subscribe the observers & Load Fact details in UI
     */
    private fun subscribeObserver() {

        factsViewModel.getFactsFromDb().observe(this, Observer { it ->

            it?.let { it ->
                supportActionBar?.title = it.title
                it.rows?.let {
                    factsAdapter.setList(it as ArrayList<FactsItem>)
                    progressBar.hide()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }
}