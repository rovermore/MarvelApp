package com.example.marvelapp.injection

import android.content.Context
import com.example.marvelapp.MarvelApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MarvelApp) {

    @Provides
    @Singleton
    fun context(): Context = app.applicationContext

}