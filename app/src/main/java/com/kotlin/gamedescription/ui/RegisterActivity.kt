package com.kotlin.gamedescription.ui

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.gamedescription.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        idRegister.setOnClickListener {
            createAccount(idEmail.text.toString(),idPassword.text.toString(),idPasswordRepeat.text.toString())
        }
    }

    private fun createAccount(email:String, password: String, passwordRepeat: String){
        if(password == passwordRepeat) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(
                            "Create Account",
                            "createUserWithEmail:success")
                        startNextActivity(Intent(this,GamesActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            "Create Account",
                            "createUserWithEmail:failure",
                            task.exception
                        )
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
        else{
            Toast.makeText(
                this, "As senhas não conferem digite novamente",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun startNextActivity(intent: Intent){
        startActivity(intent)
    }
}