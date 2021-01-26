package com.kotlin.gamedescription.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.gamedescription.R
import com.kotlin.gamedescription.model.MainViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        idCreateAccount.setOnClickListener {
            startNewActivity(Intent(this,RegisterActivity::class.java))
        }

        idLogin.setOnClickListener {
            signIn(idEmail.text.toString(), idPassword.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.mAuth.currentUser != null) {
            startNewActivity(Intent(this,GamesActivity::class.java))
        }

    }

    private fun signIn(email: String, password: String) {
        viewModel.mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    viewModel.showToast("Bem-vindo ${viewModel.mAuth.currentUser?.email.toString()}")
                    startNewActivity(Intent(this,GamesActivity::class.java))
                } else {
                    viewModel.showToast("Authentication failed")
                }
            }
    }

    private fun startNewActivity(intent: Intent){
        startActivity(intent)
    }
}