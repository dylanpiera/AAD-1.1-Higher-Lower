package com.soaringnetwork.debtchecker.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.soaringnetwork.debtchecker.MainActivity
import com.soaringnetwork.debtchecker.R
import com.soaringnetwork.debtchecker.Transaction
import kotlinx.android.synthetic.main.add_transaction_activity.*
import kotlinx.android.synthetic.main.login_activity.*
import java.time.LocalDateTime

public class AddTransactionActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_transaction_activity)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        cancel.setOnClickListener{
            finish()
        }

        submit.setOnClickListener{
            if(amountField.text.toString().isNotBlank() && spinner.selectedItem.toString() != "Dylan") {
                val amount = amountField.text.toString().toInt()
                val For = spinner.selectedItem.toString()
                val transaction =
                    Transaction(-1, amount, LocalDateTime.now().toString(), For, "Dylan")

                Log.d("Submit", transaction.toString())
                viewModel.postNewTransaction(transaction)
                Thread.sleep(500)
                setResult(204, Intent())
                finish()
            }
            else {
                Toast.makeText(this, "Please fill in the amount and select who you are paying for", Toast.LENGTH_LONG).show()
            }
        }
    }
}