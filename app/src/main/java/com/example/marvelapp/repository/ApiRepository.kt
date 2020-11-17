package com.example.marvelapp.repository

import com.example.marvelapp.client.RetrofitApiClientImplementation
import com.example.marvelapp.model.CatalogResponse
import javax.inject.Inject

class ApiRepository
    @Inject constructor(private val api: RetrofitApiClientImplementation): Repository {

    override suspend fun getCatalogResponse(offset: Int): CatalogResponse {
        return api.getCatalogResponse(offset)

    }

    override suspend fun getDetailResponse(id: String): CatalogResponse {
        return api.getDetailResponse(id)
    }
}