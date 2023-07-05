package com.example.firstkotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity()
{
    override fun onCreate( savedInstanceState: Bundle? )
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

//        inputUsername=findViewById(R.id.loginText)
//        inputPassword=findViewById(R.id.passwordText)

        val loginPageButton = findViewById<Button>(R.id.loginPageButton)
        loginPageButton.setOnClickListener {
            Toast.makeText(this@LoginActivity, "Checking credentials...", Toast.LENGTH_LONG).show()

            val intentCamera = Intent(this, CameraActivity::class.java)
            startActivity(intentCamera)
        }

        val forgotPasswordLink = findViewById<TextView>(R.id.forgotPassword)

        forgotPasswordLink.setOnClickListener {
            launchLearnerCloud(it)
        }
    }

//    private fun launchLearnerCloud() {
//        val forgotPasswordURL = "http://learnercloud.io/forgot-password"
//
//        val intentForgotPass  = Intent(Intent.ACTION_VIEW, Uri.parse(forgotPasswordURL))
//        startActivity(intentForgotPass)
//    }

    fun launchLearnerCloud(view: View) {
        val forgotPasswordURL = "http://learnercloud.io/forgot-password"

        val intentForgotPass  = Intent(Intent.ACTION_VIEW, Uri.parse(forgotPasswordURL))
        startActivity(intentForgotPass)
    }
}