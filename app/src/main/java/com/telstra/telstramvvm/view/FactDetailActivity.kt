package com.telstra.telstramvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.telstramvvm.R
import com.telstra.telstramvvm.adapter.FactsAdapter
import com.telstra.telstramvvm.data.db.FactsDatabase
import com.telstra.telstramvvm.data.model.FactsItem
import com.telstra.telstramvvm.data.network.FactsApi
import com.telstra.telstramvvm.data.repository.FactsRepository
import com.telstra.telstramvvm.utils.*
import com.telstra.telstramvvm.viewmodel.FactsViewModel
import com.telstra.telstramvvm.viewmodel.FactsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
This is Main Activity to display Fact Details
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
     * Pull To Refresh
     */
    private fun onRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            factsViewModel.saveFacts()
        }
    }

    /**
     * ViewModel Initialize
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
     * Recyclerview Initialize
     */
    private fun initRecyclerView() {
        linearLayout = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayout
        factsAdapter = FactsAdapter(context = this)
        recycler_view.adapter = factsAdapter
    }

    private fun subscribeObserver() {
        /**
         * Subscribe the observers & Load Fact details in UI
         */
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