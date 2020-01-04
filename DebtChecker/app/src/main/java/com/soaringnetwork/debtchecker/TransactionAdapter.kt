package com.soaringnetwork.debtchecker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class TransactionAdapter(private val transactions: ArrayList<Transaction>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return transactions.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val tvStandings: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(transaction: Transaction) {

            var date = LocalDateTime.parse(transaction.Date)
            tvStandings.text = "On: ${date.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy hh:mm"))}:\n${transaction.From} paid ${transaction.Amount} € for ${transaction.For}"
        }
    }
}
