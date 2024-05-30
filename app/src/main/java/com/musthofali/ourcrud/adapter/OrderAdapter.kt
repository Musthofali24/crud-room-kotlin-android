package com.musthofali.ourcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.musthofali.ourcrud.R
import com.musthofali.ourcrud.data.entity.Order

class OrderAdapter(private var list: List<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var dialog: Dialog? = null

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var customerName: TextView = view.findViewById(R.id.customer_name)
        var orderDate: TextView = view.findViewById(R.id.order_date)
        var totalOrder: TextView = view.findViewById(R.id.total_order)
        var status: TextView = view.findViewById(R.id.status)
        var note: TextView = view.findViewById(R.id.note)

        init {
            view.setOnClickListener {
                dialog?.onClick(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = list[position]
        holder.customerName.text = order.customerName
        holder.orderDate.text = order.orderDate
        holder.totalOrder.text = order.totalOrder.toString()
        holder.status.text = order.status
        holder.note.text = order.note
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size
}
