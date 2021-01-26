package com.kotlin.gamedescription.ui


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kotlin.gamedescription.R
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_add.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AddActivity : AppCompatActivity() {

    private lateinit var alertDialog: AlertDialog
    private lateinit var storage: StorageReference
    private lateinit var db: FirebaseFirestore
    private lateinit var cr: CollectionReference
    private val CODE_IMG = 1000
    private val game: MutableMap<String,Any> = HashMap()
    private var image = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        config()

        idAddGameImage.setOnClickListener {
            setIntent()
        }

        idSave.setOnClickListener {
            saveGame(idAddGameName.text.toString(),idAddGameYear.text.toString(),idAddGameDescription.text.toString())
        }
    }

    private fun config(){
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storage = FirebaseStorage.getInstance().getReference((System.currentTimeMillis() / 1000).toString())
        db = FirebaseFirestore.getInstance()
        cr = db.collection("games")
    }

    private fun setIntent(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Captura imagem"), CODE_IMG)
        Log.i("TAG","Cheguei")
    }

    private fun saveGame(name: String, year: String, description: String){
        Toast.makeText(this, game["image"].toString(), Toast.LENGTH_LONG).show()
        if(image != false) {
            getData()
            sendGame()
            finish()
        }
        else {
            Toast.makeText(this, "Por favor, adicione uma imagem", Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(){
        game["name"] = idAddGameName.text.toString()
        game["year"] = idAddGameYear.text.toString()
        game["description"] = idAddGameDescription.text.toString()
    }

    private fun sendGame(){
        cr.document(game["name"].toString()).set(game).addOnSuccessListener {
            Log.i("TAG","Adicionado com sucesso")
        }.addOnFailureListener {
            Log.i("TAG",it.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE_IMG){
            val uploadTask = storage.putFile(data!!.data!!)
            val task = uploadTask.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Chegando", Toast.LENGTH_SHORT).show()
                }
                storage!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uri = task.result
                    game["image"] = uri.toString()
                    image = true
                    //val url = uri!!.toString()
                    //    .substring(0, downloadUri.toString().indexOf("&token"))
                    Log.i("Link Direto ", uri.toString())
                    alertDialog.dismiss()
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