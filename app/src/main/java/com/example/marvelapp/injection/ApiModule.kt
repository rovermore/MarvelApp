package com.example.marvelapp.injection

import com.example.marvelapp.client.RetrofitApiClient
import com.example.marvelapp.client.RetrofitApiClientImplementation
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    val BASE_URL: String = "https://gateway.marvel.com/v1/public/"

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        return httpClient.addInterceptor(logging).build()
    }

    @Provides
    fun getRetrofitInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun marvelRetrofitApiClient(retrofit: Retrofit): RetrofitApiClientImplementation {
        return RetrofitApiClientImplementation(retrofit.create(RetrofitApiClient::class.java))
    }
}