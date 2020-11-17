package com.example.marvelapp.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnection(private val context: Context?) {
    fun isNetworkConnected(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm?.isDefaultNetworkActive ?: false
    }
}