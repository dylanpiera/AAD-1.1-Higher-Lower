package com.example.rockpaperscissorskotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissorskotlin.model.Game
import kotlinx.android.synthetic.main.game_item.view.*

class GameAdapter (private val games: List<Game>, private val screenHeight: Int) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(game: Game) {
            itemView.clGameItem.maxHeight = screenHeight/4
            itemView.tvResult.text = game.getResultText()
            itemView.ivPlayerChoice.setImageDrawable(itemView.context.getDrawable(game.playerMove.image))
            itemView.ivComputerChoice.setImageDrawable(itemView.context.getDrawable(game.computerMove.image))
            itemView.tvDate.text = game.date.toString()
        }
    }
}