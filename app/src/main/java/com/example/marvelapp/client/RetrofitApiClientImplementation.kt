package com.example.marvelapp.client

import com.example.marvelapp.model.CatalogResponse
import javax.inject.Inject

class RetrofitApiClientImplementation
    @Inject constructor(private val retrofitApiClient: RetrofitApiClient):
    RetrofitApiClient {

    override suspend fun getCatalogResponse(offset: Int): CatalogResponse {
        return retrofitApiClient.getCatalogResponse(offset)
    }

    override suspend fun getDetailResponse(id: String): CatalogResponse {
        return retrofitApiClient.getDetailResponse(id)
    }
}