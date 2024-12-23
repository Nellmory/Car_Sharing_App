package com.example.myapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rentButton: Button = findViewById(R.id.rentButton)

        rentButton.setOnClickListener {
            setContentView(R.layout.activity_rent_car)
        }
    }
}