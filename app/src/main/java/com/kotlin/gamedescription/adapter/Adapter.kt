package com.kotlin.digitalhousefood

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.gamedescription.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_add.*
import java.util.ArrayList

class Adapter(val listener: OnClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val gameList = arrayListOf<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_game, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = gameList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var game = gameList.get(position)
        Picasso.get().load(game.img)
            .fit()
            .centerCrop()
            .into(holder.imgGame)
        holder.gameName.text = game.name
        holder.gameYear.text = game.year
    }

    fun addList(list: ArrayList<Game>){
        gameList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnClickListener{
        fun onClick(position: Int)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var imgGame: ImageView = itemView.findViewById(R.id.idImgGame)
        var gameName: TextView = itemView.findViewById(R.id.idNameGame)
        var gameYear: TextView = itemView.findViewById(R.id.idYearGame)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION)
                listener.onClick(position)
        }
    }
}