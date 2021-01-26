package com.kotlin.gamedescription.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.digitalhousefood.Adapter
import com.kotlin.gamedescription.R
import kotlinx.android.synthetic.main.activity_games.*

class GamesActivity : AppCompatActivity(), Adapter.OnClickListener {

    private var adapter = Adapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        if (activeNetwork == null){
            idNoConnection.setBackgroundResource(R.drawable.no_internet_connection)
        }
        else{
            idNoConnection.setBackgroundResource(R.color.grey)
            rvGame.adapter = adapter
            rvGame.layoutManager = GridLayoutManager(this,2)
            idAddGame.setOnClickListener{
                startNewActivity(Intent(this,AddActivity::class.java))
            }
        }
    }

    override fun onClick(position: Int) {
        //val prato = listaPrato[position]
        //val intent = Intent(this, ActivityDescricao::class.java)
        //intent.putExtra("imgPrato", prato.img)
        //intent.putExtra("nomePrato", prato.nome)
        //intent.putExtra("descricaoPrato", prato.descricao)
        //startActivity(intent)
    }

    private fun startNewActivity(intent: Intent){
        startActivity(intent)
    }
}