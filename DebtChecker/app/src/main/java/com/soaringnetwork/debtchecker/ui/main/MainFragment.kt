package com.soaringnetwork.debtchecker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.soaringnetwork.debtchecker.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlin.random.Random

class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshName.setOnClickListener{
            viewModel.getAllTransactions()
        }



        nextPage.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_standingFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.transactions.observe(this, Observer {
            randUserName.text = it[Random.nextInt(it.size)]?.From
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.getAllTransactions();
    }



}
