package com.example.marvelapp.repository

import com.example.marvelapp.model.CatalogResponse

interface Repository {

    suspend fun getCatalogResponse(offset: Int): CatalogResponse

    suspend fun getDetailResponse(id: String): CatalogResponse
}