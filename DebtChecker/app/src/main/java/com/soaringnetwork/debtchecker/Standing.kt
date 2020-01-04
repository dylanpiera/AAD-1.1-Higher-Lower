package com.soaringnetwork.debtchecker
import com.google.gson.annotations.SerializedName


data class Standing (
    @SerializedName("amount") var Amount: Int,
    @SerializedName("for") var For: String,
    @SerializedName("from") var From: String
)