package com.example.marvelapp.usecase

import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.repository.ApiRepository
import javax.inject.Inject

class DetailUseCase
    @Inject constructor(private val repository: ApiRepository): UseCaseWithParameter {

     override suspend fun requestWithParameter(id: String): CatalogResponse {
        return repository.getDetailResponse(id)
    }

}