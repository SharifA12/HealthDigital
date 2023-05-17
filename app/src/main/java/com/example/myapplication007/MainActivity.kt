package com.example.myapplication007

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var openButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openButton = findViewById(R.id.button)
        openButton.setOnClickListener {
            // Launch another activity
            val intent = Intent(this@MainActivity, Activity2::class.java)
            startActivity(intent)
        }
    }
}