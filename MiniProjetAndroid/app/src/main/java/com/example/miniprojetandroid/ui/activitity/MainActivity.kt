package com.example.miniprojetandroid.ui.ui.activitity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.R

class MainActivity : AppCompatActivity() {

    lateinit var btnGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        btnGetStarted = findViewById(R.id.buttonGetStarted)

        btnGetStarted.setOnClickListener {

            val mainIntent = Intent(this, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }


    }
}