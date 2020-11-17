package com.example.marvelapp.client

import com.example.marvelapp.model.CatalogResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiClient {

    @GET("characters?ts=1&apikey=79795d874a65e1d50d495d4d95667f8f&hash=21a135ebb8f877d130c0b8ab7109e48f")
    suspend fun getCatalogResponse(@Query("offset") offset: Int)
            : CatalogResponse

    @GET("characters/{id}?ts=1&apikey=79795d874a65e1d50d495d4d95667f8f&hash=21a135ebb8f877d130c0b8ab7109e48f")
    suspend fun getDetailResponse(@Path("id") id: String)
            : CatalogResponse
}