package com.example.firstkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity()
{
//    private lateinit var inputUsername: EditText
//    private lateinit var inputPassword: EditText
//    private lateinit var btnLogin: Button

    override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

//        inputUsername=findViewById(R.id.loginText)
//        inputPassword=findViewById(R.id.passwordText)

        val loginPageButton = findViewById<Button>(R.id.loginPageButton)
        loginPageButton.setOnClickListener {
            Toast.makeText(this@LoginActivity, "Checking credentials...", Toast.LENGTH_LONG).show()
        }
    }
}