package com.telstra.telstramvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.telstra.telstramvvm.R
import com.telstra.telstramvvm.adapter.FactsAdapter
import com.telstra.telstramvvm.data.db.FactsDatabase
import com.telstra.telstramvvm.data.model.FactsItem
import com.telstra.telstramvvm.data.network.FactsApi
import com.telstra.telstramvvm.data.repository.FactsRepository
import com.telstra.telstramvvm.viewmodel.FactsViewModel
import com.telstra.telstramvvm.viewmodel.FactsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
This is Main Activity to display Fact Details
 */
class FactsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.visibility = ProgressBar.VISIBLE
        val api = FactsApi()
        val db = FactsDatabase(this)
        val factsRepository = FactsRepository(api, db)
        val factory =
            FactsViewModelFactory(factsRepository)
        // initialize viewmodel
        val viewModel = ViewModelProvider(this, factory).get(FactsViewModel::class.java)
        // show data from Viewmodel to UI
        viewModel.saveFacts()
        val linearLayout = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayout
        val adapter = FactsAdapter(context = this)
        recycler_view.adapter = adapter

        // Fetch and load UI on pull to refresh
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.saveFacts()
        }

        /**
         * Subscribe the observers & Load Fact details in UI
         */
        viewModel.getFactsFromDb().observe(this, Observer { it ->

            it?.let { it ->
                supportActionBar?.title = it.title
                it.rows?.let {
                    adapter.setList(it as ArrayList<FactsItem>)
                    progressBar.visibility = ProgressBar.GONE
                    swipeRefreshLayout.isRefreshing = false
                }
            }

        })

    }
}