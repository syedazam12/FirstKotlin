package com.example.firstkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            Toast.makeText(this@MainActivity, "Redirecting..", Toast.LENGTH_SHORT).show()
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }


        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
//            Toast.makeText(this@MainActivity, "Are you sure you want to register?", Toast.LENGTH_LONG).show()
            val intentSignup = Intent(this, SignupActivity::class.java)
            startActivity(intentSignup)
        }
    }
}