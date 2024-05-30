package com.musthofali.ourcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musthofali.ourcrud.data.AppDatabase
import com.musthofali.ourcrud.data.entity.Order

class OrderActivity : AppCompatActivity() {
    private lateinit var customerName: EditText
    private lateinit var totalOrder: EditText
    private lateinit var orderDate: EditText
    private lateinit var status: Spinner
    private lateinit var note: EditText
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        customerName = findViewById(R.id.customer_name)
        totalOrder = findViewById(R.id.total_order)
        orderDate = findViewById(R.id.order_date)
        status = findViewById(R.id.status)
        note = findViewById(R.id.note)
        btnSave = findViewById(R.id.btn_save)
        btnBack = findViewById(R.id.btn_back)
        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent != null) {
            val id = intent.getInt("id", 0)
            if (id != 0) {
                val order = database.orderDao().get(id)
                customerName.setText(order.customerName)
                totalOrder.setText(order.totalOrder.toString())
                orderDate.setText(order.orderDate)
                note.setText(order.note)
                setStatusSpinner(order.status)
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSave.setOnClickListener {
            if (totalOrder.text.isNotEmpty() && orderDate.text.isNotEmpty() && status.selectedItem != null) {
                val selectedStatus = status.selectedItem.toString()
                if (intent != null && intent.getInt("id", 0) != 0) {
                    database.orderDao().update(
                        Order(
                            intent.getInt("id"),
                            customerName.text.toString(),
                            totalOrder.text.toString().toInt(),
                            orderDate.text.toString(),
                            selectedStatus,
                            note.text.toString()
                        )
                    )
                } else {
                    database.orderDao().insertAll(
                        Order(
                            null,
                            customerName.text.toString(),
                            totalOrder.text.toString().toInt(),
                            orderDate.text.toString(),
                            selectedStatus,
                            note.text.toString()
                        )
                    )
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "Please fill in all data correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setStatusSpinner(status: String?) {
        val statuses = resources.getStringArray(R.array.spinner_status)
        status?.let {
            val index = statuses.indexOf(it)
            if (index != -1) {
                findViewById<Spinner>(R.id.status).setSelection(index)
            }
        }
    }
}
