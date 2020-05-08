package com.telstra.mvvmdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.descriptions
import kotlinx.android.synthetic.main.item_layout.view.imageUrl
import kotlinx.android.synthetic.main.item_layout.view.rowtitle
import com.bumptech.glide.Glide
import com.telstra.mvvmdemo.R
import com.telstra.mvvmdemo.data.model.FactsItem

/** Class Adapter to populate items */
class FactsAdapter(private val context: Context) :
    RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    private var listFactsItem: List<FactsItem> = ArrayList()

    /**  Adapter class to display data in recyclerview.  */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return FactViewHolder(view, context)
    }

    /** Return item counts of factItems. */
    override fun getItemCount(): Int {
        return listFactsItem.size
    }

    /** Bind Data to ViewHolder */
    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bindView(listFactsItem[position])
    }

    /** set items in list and update dataset */
    fun setList(items: ArrayList<FactsItem>) {
        listFactsItem = items
        notifyDataSetChanged()
    }

    /**
     * This Class displays FactItems in RecyclerView
     * Used Glide Library for  Lazy ImageLoading
     */
    class FactViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(factsItem: FactsItem) {
            when {
                !factsItem.title.isNullOrEmpty() -> itemView.rowtitle.text = factsItem.title
                else -> itemView.rowtitle.text = context.getString(R.string.noData)
            }
            when {
                !factsItem.description.isNullOrEmpty() -> itemView.descriptions.text =
                    factsItem.description
                else -> itemView.descriptions.text = context.getString(R.string.noDescription)
            }
            val aUrl: String = factsItem.imageHref!!.replace("http", "https")
            when {
                !factsItem.imageHref.isNullOrEmpty() -> {
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