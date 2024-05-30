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
import com.musthofali.ourcrud.adapter.ProductAdapter
import com.musthofali.ourcrud.data.AppDatabase
import com.musthofali.ourcrud.data.entity.Product

class MainProductActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_product)

        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)
        database = AppDatabase.getInstance(applicationContext)
        adapter = ProductAdapter(list)

        adapter.setDialog(object : ProductAdapter.Dialog {
            override fun onClick(position: Int) {
                val options = arrayOf("Edit", "Delete", "Cancel")
                AlertDialog.Builder(this@MainProductActivity).apply {
                    setTitle(list[position].productName)
                    setItems(options) { dialog, which ->
                        when (which) {
                            0 -> {
                                val intent = Intent(this@MainProductActivity, ProductActivity::class.java)
                                intent.putExtra("id", list[position].pid)
                                startActivity(intent)
                            }
                            1 -> {
                                database.productDao().delete(list[position])
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
            startActivity(Intent(this, ProductActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        list.clear()
        list.addAll(database.productDao().getAll())
        adapter.notifyDataSetChanged()
    }
}
