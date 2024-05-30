package com.musthofali.ourcrud

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.musthofali.ourcrud.adapter.OrderAdapter
import com.musthofali.ourcrud.data.AppDatabase
import com.musthofali.ourcrud.data.entity.Order

class MainOrderActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<Order>()
    private lateinit var adapter: OrderAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_order)

        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)
        database = AppDatabase.getInstance(applicationContext)
        adapter = OrderAdapter(list)

        adapter.setDialog(object : OrderAdapter.Dialog {
            override fun onClick(position: Int) {
                val options = arrayOf("Edit", "Delete", "Cancel")
                AlertDialog.Builder(this@MainOrderActivity).apply {
                    setTitle("Order ID: ${list[position].oid}")
                    setItems(options) { dialog, which ->
                        when (which) {
                            0 -> {
                                val intent = Intent(this@MainOrderActivity, OrderActivity::class.java)
                                intent.putExtra("id", list[position].oid)
                                startActivity(intent)
                            }
                            1 -> {
                                database.orderDao().delete(list[position])
                                getData()
                            }
                            2 -> dialog.dismiss()
                        }
                    }
                    create().show()
                }
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))

        fab.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        list.clear()
        list.addAll(database.orderDao().getAll())
        adapter.notifyDataSetChanged()
    }
}
