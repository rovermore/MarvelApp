package com.example.marvelapp.injection

import com.example.marvelapp.repository.ApiRepository
import com.example.marvelapp.usecase.CatalogListUseCase
import com.example.marvelapp.usecase.DetailUseCase

import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getCatalogListUseCase(repository: ApiRepository): CatalogListUseCase =
        CatalogListUseCase(repository)

    @Provides
    fun getDetailUseCase(repository: ApiRepository): DetailUseCase =
        DetailUseCase(repository)
}