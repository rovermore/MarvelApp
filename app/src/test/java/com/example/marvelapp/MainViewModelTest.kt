package com.example.marvelapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.screen.main.MainViewModel
import com.example.marvelapp.usecase.CatalogListUseCase
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
class MainViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var catalogListUseCase: CatalogListUseCase
    lateinit var mainViewModel: MainViewModel
    lateinit var networkConnection: NetworkConnection

    private val mockedCatalogResponse: CatalogResponse = CatalogResponseMock.catalogResponse
    private val emptyCatalogResponse: CatalogResponse = CatalogResponseMock.emptyCatalogResponse

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        catalogListUseCase = Mockito.mock(CatalogListUseCase::class.java)
        networkConnection = Mockito.mock(NetworkConnection::class.java)
        whenever(catalogListUseCase.requestWithParameter("0")).thenReturn(mockedCatalogResponse)
        whenever(networkConnection.isNetworkConnected()).thenReturn(true)
        mainViewModel = MainViewModel(networkConnection,catalogListUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `checks uiModel is null when catalog request is empty`() = runBlocking {
        whenever(catalogListUseCase.requestWithParameter("0")).thenReturn(emptyCatalogResponse)
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiModel.value).isNull()
    }

    @Test
    fun `checks uiModel is not empty when catalog request is not empty`() = runBlocking {
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiModel.value).isNotEmpty()
    }

    @Test
    fun `checks uiState is Error when catalog request is empty`() = runBlocking {
        whenever(catalogListUseCase.requestWithParameter("0")).thenReturn(emptyCatalogResponse)
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiState.value).isEqualTo(ScreenState.Error)
    }

    @Test
    fun `checks uiState is Success when catalog request is not empty`() = runBlocking {
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiState.value).isEqualTo(ScreenState.Success)
    }
}