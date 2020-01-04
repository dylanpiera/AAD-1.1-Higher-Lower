package com.soaringnetwork.debtchecker.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soaringnetwork.debtchecker.Transaction
import com.soaringnetwork.debtchecker.TransactionRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val transactionRepository = TransactionRepository()
    val transactions = MutableLiveData<Array<Transaction>>()
    val transaction = MutableLiveData<Transaction>()
    val error = MutableLiveData<String>()

    fun getAllTransactions() {
        transactionRepository.getAllTransactions().enqueue(object : Callback<Array<Transaction>> {
            override fun onResponse(call: Call<Array<Transaction>>, response: Response<Array<Transaction>>) {
                Log.d("Response", response.body().toString())
                if (response.isSuccessful) transactions.value = response.body()
                else error.value = "An error occurred: ${response.errorBody().toString()}"
            }

            override fun onFailure(call: Call<Array<Transaction>>, t: Throwable) {
                error.value = t.message
            }
        })
    }

    fun postNewTransaction(t: Transaction) {
        transactionRepository.postNewTransaction(t).enqueue(object : Callback<Transaction> {
            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                error.value = t.message
            }

            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                if(response.body() != null) {
                    transaction.value = response.body()
                }
            }

        })
    }
}
