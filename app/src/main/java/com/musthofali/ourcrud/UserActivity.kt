package com.musthofali.ourcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.musthofali.ourcrud.data.AppDatabase
import com.musthofali.ourcrud.data.entity.User

class UserActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var hobbies: List<CheckBox>
    private lateinit var spinnerMajor: Spinner
    private lateinit var btnSave: Button
    private lateinit var btnBack: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnSave = findViewById(R.id.btn_save)
        btnBack = findViewById(R.id.btn_back)
        spinnerMajor = findViewById(R.id.spinnerMajor)

        hobbies = listOf(
            findViewById(R.id.checkbox1),
            findViewById(R.id.checkbox2),
            findViewById(R.id.checkbox3),
            findViewById(R.id.checkbox4),
            findViewById(R.id.checkbox5),
            findViewById(R.id.checkbox6)
        )

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        if (intent != null) {
            val id = intent.getInt("id", 0)
            val user = database.userDao().get(id)

            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)

            val userHobbies = user.hobbies?.split(", ") ?: emptyList()
            for (hobby in hobbies) {
                if (userHobbies.contains(hobby.text.toString())) {
                    hobby.isChecked = true
                }
            }

            val majorArray = resources.getStringArray(R.array.spinner_items)
            spinnerMajor.setSelection(majorArray.indexOf(user.major))
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSave.setOnClickListener {
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                val selectedHobbies = hobbies.filter { it.isChecked }.joinToString(", ") { it.text.toString() }
                val selectedMajor = spinnerMajor.selectedItem.toString()

                if (intent != null) {
                    // Edit Data
                    database.userDao().update(
                        User(
                            intent.getInt("id"),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                            selectedHobbies,
                            selectedMajor
                        )
                    )
                } else {
                    // Add Data
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                            selectedHobbies,
                            selectedMajor
                        )
                    )
                }
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan isi semua data dengan valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
