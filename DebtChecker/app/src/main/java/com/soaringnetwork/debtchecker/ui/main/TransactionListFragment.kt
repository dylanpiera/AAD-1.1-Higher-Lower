package com.soaringnetwork.debtchecker.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soaringnetwork.debtchecker.R
import com.soaringnetwork.debtchecker.Transaction
import com.soaringnetwork.debtchecker.TransactionAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.standing_fragment.*
import kotlinx.android.synthetic.main.transaction_list_fragment.*
import kotlin.random.Random

class TransactionListFragment : Fragment() {


    companion object {
        fun newInstance() = TransactionListFragment()
    }

    private lateinit var viewModel: StandingViewModel
    private val transactions = ArrayList<Transaction>()
    private val transactionAdapter = TransactionAdapter(transactions)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.transaction_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var _viewModel = activity?.let {ViewModelProviders.of(it).get(StandingViewModel::class.java)}//ViewModelProviders.of(this).get(StandingViewModel::class.java)
        if (_viewModel != null) {
            viewModel = _viewModel
        }

        viewModel?.transactionListUser?.observe(this, Observer<String> { t ->
            Log.d("User:",t)
            (activity as AppCompatActivity).supportActionBar?.title = t
        })

        transactionList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        transactionList.adapter = transactionAdapter

        transactionList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(transactionList)

        viewModel?.transactions?.observe(this, Observer { t ->

            var x = 0

            transactions.clear()
            t.forEach{
                if(it.From == viewModel?.transactionListUser?.value || (it.From == "Dylan" && it.For == viewModel?.transactionListUser?.value)) {
                    if(it.From == "Dylan") {
                        x -= it.Amount
                    }
                    else {
                        x += it.Amount
                    }
                    transactions.add(it)
                }
            }


            if(x > 0) {
                total.setTextColor(Color.parseColor("#ff0000"))
                total.text = "You owe ${viewModel?.transactionListUser?.value} $x €"
            }
            else if(x < 0) {
                total.setTextColor(Color.parseColor("#00ff00"))
                total.text = "${viewModel?.transactionListUser?.value} owes you ${x * -1} €"
            }
            else {
                total.setTextColor(Color.parseColor("#000000"))
                total.text = "You are even with each other!"
            }

            transactionAdapter.notifyDataSetChanged()
            Log.d("Observe", transactionAdapter.itemCount.toString())
        })
        viewModel?.error?.observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteTransaction(transactions[position])
            }
        }
        return ItemTouchHelper(callback)
    }

}
