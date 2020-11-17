package com.example.marvelapp.usecase

interface UseCase {

    suspend fun request(): Any

}