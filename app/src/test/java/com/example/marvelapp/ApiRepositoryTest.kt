package com.example.marvelapp

import com.example.marvelapp.client.RetrofitApiClientImplementation
import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.repository.ApiRepository
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import junit.framework.Assert.assertEquals

class ApiRepositoryTest {

    lateinit var retrofitApiClientImplementation: RetrofitApiClientImplementation
    lateinit var apiRepository: ApiRepository

    private val catalogResponse: CatalogResponse = CatalogResponseMock.catalogResponse

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        retrofitApiClientImplementation = Mockito.mock(RetrofitApiClientImplementation::class.java)
        whenever(retrofitApiClientImplementation.getCatalogResponse(20)).thenReturn(this@ApiRepositoryTest.catalogResponse)
        whenever(retrofitApiClientImplementation.getDetailResponse("100")).thenReturn(this@ApiRepositoryTest.catalogResponse)
        apiRepository = ApiRepository(retrofitApiClientImplementation)
    }


    @Test
    fun `if RetrofitApiClientImplementation return transactions then ApiRepository calls getCatalogResponse method returns same transactions`() = runBlockingTest {
        val catalogFromClientImpl = apiRepository.getCatalogResponse(20)
        Assert.assertEquals(catalogFromClientImpl, this@ApiRepositoryTest.catalogResponse)
        assertEquals(catalogFromClientImpl.data!!.results[0].id, this@ApiRepositoryTest.catalogResponse.data!!.results[0].id)
        assertEquals(catalogFromClientImpl.data!!.results[0].name, this@ApiRepositoryTest.catalogResponse.data!!.results[0].name)
        assertEquals(catalogFromClientImpl.data!!.results[0].description, this@ApiRepositoryTest.catalogResponse.data!!.results[0].description)
        assertEquals(catalogFromClientImpl.data!!.results[0].image!!.path, this@ApiRepositoryTest.catalogResponse.data!!.results[0].image!!.path)
    }

    @Test
    fun `if RetrofitApiClientImplementation return transactions then ApiRepository calls getDetailResponse method returns same transactions`() = runBlockingTest {
        val detailFromClientImpl = apiRepository.getDetailResponse("100")
        Assert.assertEquals(detailFromClientImpl, this@ApiRepositoryTest.catalogResponse)
        assertEquals(detailFromClientImpl.data!!.results[0].id, this@ApiRepositoryTest.catalogResponse.data!!.results[0].id)
        assertEquals(detailFromClientImpl.data!!.results[0].name, this@ApiRepositoryTest.catalogResponse.data!!.results[0].name)
        assertEquals(detailFromClientImpl.data!!.results[0].description, this@ApiRepositoryTest.catalogResponse.data!!.results[0].description)
        assertEquals(detailFromClientImpl.data!!.results[0].image!!.path, this@ApiRepositoryTest.catalogResponse.data!!.results[0].image!!.path)
    }
}