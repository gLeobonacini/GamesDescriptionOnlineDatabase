package com.kotlin.gamedescription.ui

import android.R.attr.data
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.digitalhousefood.Game
import com.kotlin.gamedescription.R
import com.kotlin.gamedescription.model.MainViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_add.*


class AddActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val CODE_IMG = 1000
    private val game: MutableMap<String,String> = HashMap()
    private var image = false
    private lateinit var gameIntent: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        if (intent != null && intent.extras != null) {
            val gameIntent = Game(
                Uri.parse(intent.getStringExtra("imgGame")),
                intent.getStringExtra("nameGame")!!,
                intent.getStringExtra("yearGame")!!,
                intent.getStringExtra("descriptionGame")!!)
            putData(gameIntent)
        }

        idAddGameImage.setOnClickListener {
            setIntent()
        }

        idSave.setOnClickListener {
            saveGame(idAddGameName.text.toString(),idAddGameYear.text.toString(),idAddGameDescription.text.toString())
        }
    }

    private fun putData(gameIntent: Game){
        idCamera.visibility = View.INVISIBLE
        image = true
        game["image"] = gameIntent.img.toString()
        Picasso.get().load(gameIntent.img)
            .fit()
            .centerCrop()
            .transform(CropCircleTransformation())
            .into(idAddGameImage)
        idAddGameName.setText(gameIntent.name)
        idAddGameYear.setText(gameIntent.year)
        idAddGameDescription.setText(gameIntent.description)
    }

    private fun setIntent(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Captura imagem"), CODE_IMG)
    }

    private fun saveGame(name: String, year: String, description: String){
        Toast.makeText(this, game["image"].toString(), Toast.LENGTH_LONG).show()
        if(image != false) {
            getData()
            sendGame()
            viewModel.getDataDB()
            finish()
        }
        else {
            viewModel.showToast("Por favor, adicione uma imagem")
        }
    }

    private fun getData(){
        game["name"] = idAddGameName.text.toString()
        game["year"] = idAddGameYear.text.toString()
        game["description"] = idAddGameDescription.text.toString()
    }

    private fun sendGame(){
        viewModel.cr.document(game["name"].toString()).set(game).addOnSuccessListener {
            Log.i("TAG","Adicionado com sucesso")
        }.addOnFailureListener {
            Log.i("TAG",it.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE_IMG){
            val uploadTask = viewModel.storage.putFile(data!!.data!!)
            val task = uploadTask.continueWithTask { task ->
                viewModel.storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uri = task.result
                    game["image"] = uri.toString()
                    image = true
                    //Log.i("Link Direto ", uri.toString())
                    viewModel.alertDialog.dismiss()
                    idCamera.visibility = View.INVISIBLE

                    Picasso.get().load(uri)
                        .fit()
                        .centerCrop()
                        .transform(CropCircleTransformation())
                        .into(idAddGameImage)
                }
            }
        }
    }
}