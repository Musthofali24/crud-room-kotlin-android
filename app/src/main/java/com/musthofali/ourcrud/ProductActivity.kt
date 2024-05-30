package com.musthofali.ourcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musthofali.ourcrud.data.AppDatabase
import com.musthofali.ourcrud.data.entity.Product

class ProductActivity : AppCompatActivity() {
    private lateinit var productName: EditText
    private lateinit var price: EditText
    private lateinit var stock: EditText
    private lateinit var category: Spinner
    private lateinit var ratingGroup: RadioGroup
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productName = findViewById(R.id.product_name)
        price = findViewById(R.id.price)
        stock = findViewById(R.id.stock)
        category = findViewById(R.id.category)
        ratingGroup = findViewById(R.id.rating_radio_group)
        btnSave = findViewById(R.id.btn_save)
        btnBack = findViewById(R.id.btn_back)
        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent != null) {
            val id = intent.getInt("id", 0)
            if (id != 0) {
                val product = database.productDao().get(id)
                productName.setText(product.productName)
                price.setText(product.price)
                stock.setText(product.stock)
                category.setSelection(getCategoryIndex(product.category))
                setRatingRadioButton(product.rating)
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSave.setOnClickListener {
            if (productName.text.isNotEmpty() && price.text.isNotEmpty() && category.selectedItem != null) {
                val selectedCategory = category.selectedItem.toString()
                val selectedRating = getSelectedRating()

                if (intent != null && intent.getInt("id", 0) != 0) {
                    database.productDao().update(
                        Product(
                            intent.getInt("id"),
                            productName.text.toString(),
                            price.text.toString(),
                            stock.text.toString(),
                            selectedCategory,
                            selectedRating
                        )
                    )
                } else {
                    database.productDao().insertAll(
                        Product(
                            null,
                            productName.text.toString(),
                            price.text.toString(),
                            stock.text.toString(),
                            selectedCategory,
                            selectedRating
                        )
                    )
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "Please fill in all data correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCategoryIndex(category: String?): Int {
        val categories = resources.getStringArray(R.array.spinner_products)
        return categories.indexOf(category)
    }

    private fun setRatingRadioButton(rating: Int) {
        val radioButtonId = when (rating) {
            1 -> R.id.rating_1
            2 -> R.id.rating_2
            3 -> R.id.rating_3
            4 -> R.id.rating_4
            5 -> R.id.rating_5
            else -> -1
        }
        if (radioButtonId != -1) {
            findViewById<RadioButton>(radioButtonId).isChecked = true
        }
    }

    private fun getSelectedRating(): Int {
        return when (ratingGroup.checkedRadioButtonId) {
            R.id.rating_1 -> 1
            R.id.rating_2 -> 2
            R.id.rating_3 -> 3
            R.id.rating_4 -> 4
            R.id.rating_5 -> 5
            else -> 0
        }
    }
}
