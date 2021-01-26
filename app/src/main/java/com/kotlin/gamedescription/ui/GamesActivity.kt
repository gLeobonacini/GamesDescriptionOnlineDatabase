package com.kotlin.gamedescription.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlin.digitalhousefood.Adapter
import com.kotlin.gamedescription.R
import com.kotlin.gamedescription.model.MainViewModel
import kotlinx.android.synthetic.main.activity_games.*
import androidx.activity.viewModels

class GamesActivity : AppCompatActivity(), Adapter.OnClickListener {

    private val viewModel:MainViewModel by viewModels()
    private var adapter = Adapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        if (viewModel.internetConnection()){
            idNoConnection.setBackgroundResource(R.drawable.no_internet_connection)
        }
        else{
            recyclerViewConfig()

            viewModel.gameList.observeForever{
                adapter.addList(it)
            }

            idAddGame.setOnClickListener{
                startNewActivity(Intent(this,AddActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDataDB()
    }

    private fun recyclerViewConfig(){
        idNoConnection.setBackgroundResource(R.color.grey)
        rvGame.adapter = adapter
        rvGame.layoutManager = GridLayoutManager(this,2)
    }

    override fun onClick(position: Int) {
        val game = viewModel.gameList.value!![position]
        Log.i("TAG",game.toString())
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("imgGame", game.img.toString())
        intent.putExtra("nameGame", game.name)
        intent.putExtra("yearGame", game.year)
        intent.putExtra("descriptionGame", game.description)
        startNewActivity(intent)
    }

    private fun startNewActivity(intent: Intent){
        startActivity(intent)
    }
}