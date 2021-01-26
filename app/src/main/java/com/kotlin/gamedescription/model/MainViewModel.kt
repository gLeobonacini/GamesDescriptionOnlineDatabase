package com.kotlin.gamedescription.model

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.kotlin.digitalhousefood.Game
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val contexto = getApplication<Application>().applicationContext
    val gameList = MutableLiveData<ArrayList<Game>>()
    val gamelist: ArrayList<Game> = arrayListOf()
    var mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val cr = db.collection("games")
    var alertDialog = SpotsDialog.Builder().setContext(contexto).build()
    var storage = FirebaseStorage.getInstance().getReference((System.currentTimeMillis() / 1000).toString())


    fun internetConnection(): Boolean{
        val cm = contexto.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return (activeNetwork == null)
    }

    fun getDataDB(){
        var game: Game
        viewModelScope.launch{
            db.collection("games").get().addOnCompleteListener {task->
                if(task.isSuccessful){
                    for (document in task.result!!){
                        var game = Game(Uri.parse(document.data["image"].toString()),
                                document.data["name"].toString(),
                                document.data["year"].toString(),
                                document.data["description"].toString())
                        gamelist.add(game)
                    }
                    gameList.value = gamelist
                    //Log.i("TAG","=> " + gameList.value.toString())
                }else{
                    Log.e("TAG", "Error getting documents.", task.exception)
                }
            }
        }

    }

    fun showToast(msg:String){
        Toast.makeText(contexto,msg,Toast.LENGTH_LONG).show()
    }
}