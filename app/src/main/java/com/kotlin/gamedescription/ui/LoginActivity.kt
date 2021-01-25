package com.kotlin.gamedescription.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.gamedescription.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        idCreateAccount.setOnClickListener {
            startNextActivity(Intent(this,RegisterActivity::class.java))
        }

        idLogin.setOnClickListener {
            signIn(idEmail.text.toString(),idPassword.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser != null){
            startNextActivity(Intent(this,RegisterActivity::class.java))
        }

    }

    private fun signIn(email:String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Firebase Auth Sign In", "signInWithEmail:success")
                    val user = mAuth.currentUser
                    startNextActivity(Intent(this,GamesActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Firebase Auth Sign In", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    private fun startNextActivity(intent: Intent){
        startActivity(intent)
    }
}