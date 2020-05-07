package com.telstra.telstramvvm.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
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
if(isNetworkAvailable(applicationContext)) {
    viewModel.saveFacts()
}else
{
    Toast.makeText(
        applicationContext,
        applicationContext.getString(R.string.noconnectivity),
        Toast.LENGTH_SHORT
    )?.show()
    progressBar.visibility = ProgressBar.GONE

}
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

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}