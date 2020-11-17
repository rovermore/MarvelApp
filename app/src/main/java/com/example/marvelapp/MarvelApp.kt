package com.example.marvelapp

import android.app.Application
import com.example.marvelapp.injection.*

class MarvelApp: Application() {

    companion object {
        lateinit var mDaggerAppComponent: AppComponent
        fun daggerAppComponent():AppComponent = mDaggerAppComponent
    }


    override fun onCreate() {
        super.onCreate()
        mDaggerAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule())
            .apiRepositoryModule(ApiRepositoryModule())
            .useCaseModule(UseCaseModule())
            .networkConnectionModule(NetworkConnectionModule())
            .build()

    }
}
