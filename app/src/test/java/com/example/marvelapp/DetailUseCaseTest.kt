package com.example.marvelapp

import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.repository.ApiRepository
import com.example.marvelapp.usecase.DetailUseCase
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailUseCaseTest {

    lateinit var apiRepository: ApiRepository
    lateinit var detailUseCase: DetailUseCase

    private val catalogResponse: CatalogResponse = CatalogResponseMock.catalogResponse

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        apiRepository = Mockito.mock(ApiRepository::class.java)
        whenever(apiRepository.getDetailResponse("100")).thenReturn(this@DetailUseCaseTest.catalogResponse)
        detailUseCase = DetailUseCase(apiRepository)
    }


    @Test
    fun `if ApiRepository return transactions then TransactionsUseCase returns same transactions`() = runBlockingTest {
        val detailResponseFromUseCase = detailUseCase.requestWithParameter("100")
        Assert.assertEquals(detailResponseFromUseCase, this@DetailUseCaseTest.catalogResponse)
        Assert.assertEquals(detailResponseFromUseCase.data!!.results[0].id, this@DetailUseCaseTest.catalogResponse.data!!.results[0].id)
        Assert.assertEquals(detailResponseFromUseCase.data!!.results[0].name, this@DetailUseCaseTest.catalogResponse.data!!.results[0].name)
        Assert.assertEquals(detailResponseFromUseCase.data!!.results[0].description, this@DetailUseCaseTest.catalogResponse.data!!.results[0].description)
        Assert.assertEquals(detailResponseFromUseCase.data!!.results[0].image!!.path, this@DetailUseCaseTest.catalogResponse.data!!.results[0].image!!.path)
        Assert.assertEquals(detailResponseFromUseCase.data!!.results[0].image!!.extension, this@DetailUseCaseTest.catalogResponse.data!!.results[0].image!!.extension)
    }
}