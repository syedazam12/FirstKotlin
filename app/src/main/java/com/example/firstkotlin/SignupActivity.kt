package com.example.firstkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        val signupPageButton = findViewById<Button>(R.id.registerPageButton)
        signupPageButton.setOnClickListener {
            Toast.makeText(this@SignupActivity, "Saving credentials...", Toast.LENGTH_LONG).show()

            val intentDirectToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentDirectToLogin)
        }
    }
}