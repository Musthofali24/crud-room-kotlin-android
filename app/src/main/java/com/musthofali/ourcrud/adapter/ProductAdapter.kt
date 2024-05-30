package com.musthofali.ourcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.musthofali.ourcrud.R
import com.musthofali.ourcrud.data.entity.Product

class ProductAdapter(private var list: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var dialog: Dialog? = null

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productName: TextView = view.findViewById(R.id.product_name)
        var price: TextView = view.findViewById(R.id.price)
        var stock: TextView = view.findViewById(R.id.stock)
        var category: TextView = view.findViewById(R.id.category)
        var rating: TextView = view.findViewById(R.id.rating)

        init {
            view.setOnClickListener {
                dialog?.onClick(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        holder.productName.text = product.productName
        holder.price.text = product.price
        holder.stock.text = product.stock
        holder.category.text = product.category
        holder.rating.text = product.rating.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size
}
