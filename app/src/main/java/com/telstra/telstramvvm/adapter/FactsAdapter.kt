package com.telstra.telstramvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.telstra.telstramvvm.R
import com.telstra.telstramvvm.data.model.RowsItem
import kotlinx.android.synthetic.main.item_layout.view.*

class FactsAdapter(private val context: Context) : RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    private var listRowsItem: List<RowsItem> = ArrayList()

    /** @Method initializing view and returning/inflating view  . */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view, context)
    }

    /** @Method return item count . */
    override fun getItemCount(): Int {

        return listRowsItem.size
    }

    /** @Method data binding in each element . */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(listRowsItem[position])
    }

    /** @Method set result in list. */

    fun setList(items: ArrayList<RowsItem>) {
        listRowsItem = items
        notifyDataSetChanged()
    }

    /** @Method return view holder . */
    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {

        fun bindView(rowsItem: RowsItem) {

            when {
                !rowsItem.title.isNullOrEmpty() -> itemView.rowtitle.text = rowsItem.title
                else -> itemView.rowtitle.text = context.getString(R.string.noData)
            }
            when {
                !rowsItem.description.isNullOrEmpty() -> itemView.descriptions.text =
                    rowsItem.description
                else -> itemView.descriptions.text = context.getString(R.string.noDescription)
            }

            val aUrl: String = rowsItem.imageHref!!.replace("http", "https")
            if (!rowsItem.imageHref.isNullOrEmpty()) {
                try {
                    Picasso.get()
                        .load(aUrl)
                        .resize(80, 80)
                        .centerCrop()
                        .error(R.drawable.ic_broken_image)
                        .into(itemView.imageUrl)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.uncaughtexception),
                        Toast.LENGTH_SHORT
                    )?.show()
                }
            }

        }

    }
}