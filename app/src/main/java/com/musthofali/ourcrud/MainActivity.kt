package com.musthofali.ourcrud

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileButton = findViewById<ImageButton>(R.id.profileButton)
        val gradeButton = findViewById<ImageButton>(R.id.gradeButton)
        val listButton = findViewById<ImageButton>(R.id.listButton)

        profileButton.setOnClickListener {
            startActivity(Intent(this, MainUserActivity::class.java))
        }

        gradeButton.setOnClickListener {
            startActivity(Intent(this, MainOrderActivity::class.java))
        }

        listButton.setOnClickListener {
            startActivity(Intent(this, MainProductActivity::class.java))
        }

    }
}