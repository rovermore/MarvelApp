package com.example.marvelapp

import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.repository.ApiRepository
import com.example.marvelapp.usecase.CatalogListUseCase
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CatalogListUseCaseTest {

    lateinit var apiRepository: ApiRepository
    lateinit var catalogListUseCase: CatalogListUseCase

    private val catalogResponse: CatalogResponse = CatalogResponseMock.catalogResponse

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        apiRepository = Mockito.mock(ApiRepository::class.java)
        whenever(apiRepository.getCatalogResponse(20)).thenReturn(this@CatalogListUseCaseTest.catalogResponse)
        catalogListUseCase = CatalogListUseCase(apiRepository)
    }


    @Test
    fun `if ApiRepository return transactions then TransactionsUseCase returns same transactions`() = runBlockingTest {
        val catalogResponseFromUseCase = catalogListUseCase.requestWithParameter("20")
        Assert.assertEquals(catalogResponseFromUseCase, this@CatalogListUseCaseTest.catalogResponse)
        Assert.assertEquals(catalogResponseFromUseCase.data!!.results[0].id, this@CatalogListUseCaseTest.catalogResponse.data!!.results[0].id)
        Assert.assertEquals(catalogResponseFromUseCase.data!!.results[0].name, this@CatalogListUseCaseTest.catalogResponse.data!!.results[0].name)
        Assert.assertEquals(catalogResponseFromUseCase.data!!.results[0].description, this@CatalogListUseCaseTest.catalogResponse.data!!.results[0].description)
        Assert.assertEquals(catalogResponseFromUseCase.data!!.results[0].image!!.path, this@CatalogListUseCaseTest.catalogResponse.data!!.results[0].image!!.path)
        Assert.assertEquals(catalogResponseFromUseCase.data!!.results[0].image!!.extension, this@CatalogListUseCaseTest.catalogResponse.data!!.results[0].image!!.extension)
    }
}
