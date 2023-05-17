
package com.example.myapplication007

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity2 : AppCompatActivity() {
    private lateinit var openButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        openButton = findViewById(R.id.returnbutton)
        openButton.setOnClickListener {
            // Launch another activity
            val intent = Intent(this@Activity2, MainActivity::class.java)
            startActivity(intent)
        }
    }
}