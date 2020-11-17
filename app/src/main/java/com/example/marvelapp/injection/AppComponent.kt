package com.example.marvelapp.injection

import com.example.marvelapp.MainActivity
import com.example.marvelapp.screen.detail.DetailFragment
import com.example.marvelapp.screen.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        ApiRepositoryModule::class,
        NetworkConnectionModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(detailFragment: DetailFragment)
}