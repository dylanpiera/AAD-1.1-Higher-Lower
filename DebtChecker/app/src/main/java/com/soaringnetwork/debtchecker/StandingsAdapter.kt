package com.soaringnetwork.debtchecker

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StandingsAdapter(private val standings: List<Standing>, private val onClick: (Standing) -> Unit) : RecyclerView.Adapter<StandingsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return standings.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(standings[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                onClick(standings[adapterPosition])
            }
        }

        private val tvStandings: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(standing: Standing) {
            var x = standing.Amount

            if(x > 0) {
                tvStandings.setTextColor(Color.parseColor("#ff0000"))
                tvStandings.text = "You owe ${standing.From} $x €\n" +
                        "\n" +
                        "Click for more info..."
            }
            else if(x < 0) {
                tvStandings.setTextColor(Color.parseColor("#00ff00"))
                tvStandings.text = "${standing.From} owes you ${x * -1} €\n" +
                        "\n" +
                        "Click for more info..."
            }
            else {
                tvStandings.setTextColor(Color.parseColor("#000000"))
                tvStandings.text = "You are even with ${standing.From}\n" +
                        "\n" +
                        "Click for more info..."
            }
        }
    }

}