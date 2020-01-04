package com.soaringnetwork.debtchecker

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

public interface TransactionApiService {
    @GET("transaction")
    fun getAllTransactions() : Call<Array<Transaction>>

    @POST("transaction")
    fun postNewTransaction(@Body transaction: Transaction) : Call<Transaction>

    @DELETE("transaction/{id}")
    fun deleteTransaction(@Path("id") transactionId: Int) : Call<ResponseBody>
}

public class TransactionApi {
    companion object {
        private const val baseUrl = "https://api.soaringnetwork.com/"

        fun createApi(): TransactionApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val transactionApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return transactionApi.create(TransactionApiService::class.java)
        }
    }
}

public class TransactionRepository {
    private val transactionApi: TransactionApiService = TransactionApi.createApi()

    fun getAllTransactions() = transactionApi.getAllTransactions()
    fun postNewTransaction(transaction: Transaction) = transactionApi.postNewTransaction(transaction)
    fun deleteTransaction(transaction: Transaction) = transactionApi.deleteTransaction(transaction.Id)
}