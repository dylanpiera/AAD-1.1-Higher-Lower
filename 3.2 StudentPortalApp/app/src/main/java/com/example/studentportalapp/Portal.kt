package com.example.studentportalapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.net.URI

@Parcelize
data class Portal(val title: String, val uri: URI) : Parcelable