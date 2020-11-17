package com.example.marvelapp.injection

import com.example.marvelapp.repository.ApiRepository
import com.example.marvelapp.usecase.CatalogListUseCase

import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getCatalogListUseCase(repository: ApiRepository): CatalogListUseCase =
        CatalogListUseCase(repository)
}