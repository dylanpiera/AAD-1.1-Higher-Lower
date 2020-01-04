package com.soaringnetwork.debtchecker

import com.google.gson.annotations.SerializedName


data class Transaction (
    @SerializedName("id") var Id: Int,
    @SerializedName("amount") var Amount: Int,
    @SerializedName("date") var Date: String,
    @SerializedName("for") var For: String,
    @SerializedName("from") var From: String
)