package com.example.marvelapp.injection

import android.content.Context
import com.example.marvelapp.utils.NetworkConnection
import dagger.Module
import dagger.Provides

@Module
class NetworkConnectionModule {

    @Provides
    fun getNetworkConnection(context: Context): NetworkConnection =
        NetworkConnection(context)
}