package com.kotlin.gamedescription.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.gamedescription.R
import com.kotlin.gamedescription.model.MainViewModel
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        idRegister.setOnClickListener {
            createAccount(idEmail.text.toString(),idPassword.text.toString(),idPasswordRepeat.text.toString())
        }
    }

    private fun createAccount(email:String, password: String, passwordRepeat: String){
        if(password == passwordRepeat) {
            viewModel.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startNewActivity(Intent(this,GamesActivity::class.java))
                    } else {
                        viewModel.showToast("Authentication failed")
                    }
            }
        }
        else{
            viewModel.showToast("As senhas não conferem digite novamente")
        }
    }

    private fun startNewActivity(intent: Intent){
        startActivity(intent)
    }
}