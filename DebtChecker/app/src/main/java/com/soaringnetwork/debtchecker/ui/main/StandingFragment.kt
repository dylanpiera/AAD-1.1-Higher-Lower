package com.soaringnetwork.debtchecker.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soaringnetwork.debtchecker.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.standing_fragment.*
import kotlin.math.log
import kotlin.random.Random

class StandingFragment : Fragment() {

    companion object {
        fun newInstance() = StandingFragment()
    }

    private lateinit var viewModel: StandingViewModel
    private val standings = ArrayList<Standing>()
    private val standingAdapter = StandingsAdapter(standings) { standing -> onStandingClick(standing)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.standing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult", "The RC is: $requestCode")
        if(requestCode == 204) {
            viewModel.getAllTransactions()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity!!.let { ViewModelProviders.of(it).get(StandingViewModel::class.java) }


        standingList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        standingList.adapter = standingAdapter

        standingList.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        addNewTransaction.setOnClickListener {
            val intent = Intent(this.context, AddTransactionActivity::class.java)
            startActivityForResult(intent, 204)
        }

        viewModel.transactions.observe(this, Observer { transactions ->

            standings.clear()

            standings.add(Standing(0, "Dylan", "Marth"))
            standings.add(Standing(0, "Dylan", "Jeff"))
            standings.add(Standing(0, "Dylan", "Tymo"))

            transactions.forEach{
                when(it.From) {
                    "Marth" -> standings[0].Amount += it.Amount
                    "Jeff" -> standings[1].Amount += it.Amount
                    "Tymo" -> standings[2].Amount += it.Amount
                    "Dylan" -> when(it.For) {
                        "Marth" -> standings[0].Amount -= it.Amount
                        "Jeff" -> standings[1].Amount -= it.Amount
                        "Tymo" -> standings[2].Amount -= it.Amount
                    }
                }
            }



            standingAdapter.notifyDataSetChanged()
            Log.d("Observe", standingAdapter.itemCount.toString())
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })

        createItemTouchHelper().attachToRecyclerView(standingList)
        viewModel.getAllTransactions()
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {

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
                Log.d("onSwiped", "Item Swiped!")
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun onStandingClick(standing: Standing) {
        viewModel.SetTransactionListUser(standing.From)
        findNavController().navigate(R.id.action_standingFragment_to_transactionListFragment)
    }
}
