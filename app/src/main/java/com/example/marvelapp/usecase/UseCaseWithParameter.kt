package com.example.marvelapp.usecase

interface UseCaseWithParameter {
    suspend fun requestWithParameter(parameter: String): Any
}