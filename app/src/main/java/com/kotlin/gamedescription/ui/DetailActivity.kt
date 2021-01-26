package com.kotlin.gamedescription.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.digitalhousefood.Game
import com.kotlin.gamedescription.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        game = Game(Uri.parse(intent.getStringExtra("imgGame")),
            intent.getStringExtra("nameGame")!!,
            intent.getStringExtra("yearGame")!!,
            intent.getStringExtra("descriptionGame")!!)

        Picasso.get().load(game.img)
            .fit()
            .centerCrop()
            .into(idDetailGameImage)
        idDetailBigGameName.text = game.name
        idDetailSmallGameName.text = game.name
        idDetailGameYear.text = "Created at " + game.year
        idDetailGameDescription.text = game.description

        idBack.setOnClickListener {
            finish()
        }

        idDetailEdit.setOnClickListener {
            startNewActivity()
        }
    }

    private fun startNewActivity(){
        val intent = Intent(this, AddActivity::class.java)
        intent.putExtra("imgGame", game.img.toString())
        intent.putExtra("nameGame", game.name)
        intent.putExtra("yearGame", game.year)
        intent.putExtra("descriptionGame", game.description)
        startActivity(intent)
    }
}