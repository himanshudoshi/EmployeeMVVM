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
import com.telstra.telstramvvm.data.model.RowsItem
import com.telstra.telstramvvm.data.network.FactsApi
import com.telstra.telstramvvm.data.repository.FactsRepository
import com.telstra.telstramvvm.viewmodel.FactsViewModel
import com.telstra.telstramvvm.viewmodel.FactsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

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

        val viewModel = ViewModelProvider(this, factory).get(FactsViewModel::class.java)

        viewModel.saveFacts()

        val linearLayout = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayout
        val adapter = FactsAdapter(context = this)
        recycler_view.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.saveFacts()

        }
        viewModel.getFactsFromDb().observe(this, Observer { it ->

            it?.let { it ->
                supportActionBar?.title = it.title
                it.rows?.let {

                    adapter.setList(it as ArrayList<RowsItem>)
                    progressBar.visibility = ProgressBar.GONE
                    swipeRefreshLayout.isRefreshing = false

                }
            }

        })
    }
}