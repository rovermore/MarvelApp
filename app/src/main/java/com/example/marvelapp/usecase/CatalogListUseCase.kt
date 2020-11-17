package com.example.marvelapp.usecase

import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.repository.ApiRepository
import javax.inject.Inject

class CatalogListUseCase
    @Inject constructor(private val repository: ApiRepository): UseCaseWithParameter {

    override suspend fun requestWithParameter(parameter: String): CatalogResponse {
        return repository.getCatalogResponse(parameter.toInt())
    }
}