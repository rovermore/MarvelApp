package com.example.marvelapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.screen.detail.DetailViewModel
import com.example.marvelapp.usecase.DetailUseCase
import com.example.marvelapp.utils.NetworkConnection
import com.example.marvelapp.utils.ScreenState
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var detailUseCase: DetailUseCase
    lateinit var detailViewModel: DetailViewModel
    lateinit var networkConnection: NetworkConnection

    private val mockedDetailResponse: CatalogResponse = CatalogResponseMock.detailResponse
    private val emptyCatalogResponse: CatalogResponse = CatalogResponseMock.emptyCatalogResponse

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        detailUseCase = Mockito.mock(DetailUseCase::class.java)
        networkConnection = Mockito.mock(NetworkConnection::class.java)
        whenever(detailUseCase.requestWithParameter("100")).thenReturn(mockedDetailResponse)
        whenever(networkConnection.isNetworkConnected()).thenReturn(true)
        detailViewModel = DetailViewModel(networkConnection,detailUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `checks uiModel is null when catalog request is empty`() = runBlocking {
        whenever(detailUseCase.requestWithParameter("100")).thenReturn(emptyCatalogResponse)
        detailViewModel.initialize("100")
        Truth.assertThat(detailViewModel.uiModel.value).isNull()
    }

    @Test
    fun `checks uiModel is not empty when catalog request is not empty`() = runBlocking {
        detailViewModel.initialize("100")
        Truth.assertThat(detailViewModel.uiModel.value).isNotNull()
    }

    @Test
    fun `checks uiState is Error when catalog request is empty`() = runBlocking {
        whenever(detailUseCase.requestWithParameter("100")).thenReturn(emptyCatalogResponse)
        detailViewModel.initialize("100")
        Truth.assertThat(detailViewModel.uiState.value).isEqualTo(ScreenState.Error)
    }

    @Test
    fun `checks uiState is Success when catalog request is not empty`() = runBlocking {
        detailViewModel.initialize("100")
        Truth.assertThat(detailViewModel.uiState.value).isEqualTo(ScreenState.Success)
    }
}