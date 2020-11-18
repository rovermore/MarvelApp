package com.example.marvelapp.injection

import com.example.marvelapp.client.RetrofitApiClientImplementation
import com.example.marvelapp.repository.ApiRepository
import dagger.Module
import dagger.Provides

@Module
class ApiRepositoryModule {

    @Provides
    fun apiRepository(retrofitApiClientImplementation: RetrofitApiClientImplementation): ApiRepository =
        ApiRepository(retrofitApiClientImplementation)
}