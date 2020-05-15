package com.techm.employee.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techm.employee.R
import com.techm.employee.data.model.EmployeesDetails
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

/** Class Adapter to populate items */
class EmployeesAdapter(private val context: Context) :
    RecyclerView.Adapter<EmployeesAdapter.EmployeesViewHolder>(), Filterable {

    private var listEmployeesDetails = ArrayList<EmployeesDetails>()
    private var listFilteredEmployeesDetails = ArrayList<EmployeesDetails>()
    private var items: MutableList<String>? = null

  /*  init {
        listFilteredEmployeesDetails = listEmployeesDetails;
    }
*/
    /**  Adapter class to display data in recyclerview.  */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return EmployeesViewHolder(view, context)
    }

    /** Return item counts of Employees. */
    override fun getItemCount(): Int {
        return listEmployeesDetails.size
    }

    /** Bind Data to ViewHolder */
    override fun onBindViewHolder(holder: EmployeesViewHolder, position: Int) {
        holder.bindView(listEmployeesDetails[position])
    }

    /** set items in list and update dataset */
    fun setList(items: ArrayList<EmployeesDetails>) {
        listEmployeesDetails = items
        notifyDataSetChanged()
    }

    /* */
    /** Add items in list  for SwipeToDelete functionality and update dataset *//*
    fun addItem(name: String) {
        items?.add(name)
        notifyItemInserted(listEmployeesDetails.size)
    }
*/
    /** Remove items in list  for SwipeToDelete functionality and update dataset */
    fun removeAt(position: Int) {
        items?.removeAt(position)
        notifyItemRemoved(position)
    }

    /** This function is created for SearchView Functionality based on text it filters Data and shows Recyclerview */
    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filteredList: ArrayList<EmployeesDetails> = ArrayList()
                if (charString.isEmpty()) {
                    listFilteredEmployeesDetails = listEmployeesDetails
                    Log.e("All List","All List"+listFilteredEmployeesDetails)
                   // filteredList.addAll(listEmployeesDetails)
                } else {
                    for (row in listEmployeesDetails) {
                        if (row.employee_name.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(row)
                        }
                    }
                    listFilteredEmployeesDetails = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listFilteredEmployeesDetails
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
               //  listEmployeesDetails.clear()
                // listEmployeesDetails.addAll(filterResults.values as ArrayList<EmployeesDetails>)
                listEmployeesDetails = filterResults.values as ArrayList<EmployeesDetails>
                Log.e("Filtered list", "filteredlist$listEmployeesDetails")
                notifyDataSetChanged()
            }
        }
    }

    /**
     * This Class displays EmployeeItems in RecyclerView
     * Used Glide Library for  Lazy ImageLoading
     */
    class EmployeesViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(employeesDetails: EmployeesDetails) {
            when {
                !employeesDetails.employee_name.isNullOrEmpty() -> itemView.ename.text =
                    employeesDetails.employee_name
                else -> itemView.ename.text = context.getString(R.string.noData)
            }
            itemView.salary.text =
                employeesDetails.employee_salary.toString()
            itemView.age.text =
                employeesDetails.employee_age.toString()
            val aUrl: String = employeesDetails.profile_image!!.replace("http", "https")
            when {
                !employeesDetails.profile_image.isNullOrEmpty() -> {
                    try {
                        Glide.with(context)
                            .load(aUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_image_place_holder)
                            .error(R.drawable.ic_broken_image)
                            .into(itemView.imageUrl)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.somethingwentwrong),
                            Toast.LENGTH_SHORT
                        )?.show()
                    }
                }
                else -> {
                    try {
                        Glide.with(context)
                            .load(aUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_no_image)
                            .into(itemView.imageUrl)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.somethingwentwrong),
                            Toast.LENGTH_SHORT
                        )?.show()
                    }
                }
            }
        }
    }
}







