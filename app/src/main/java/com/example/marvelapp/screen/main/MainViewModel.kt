package com.example.marvelapp.screen.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.model.Character
import com.example.marvelapp.usecase.CatalogListUseCase
import com.example.marvelapp.utils.NetworkConnection
import com.example.marvelapp.utils.ScreenState
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val networkConnection: NetworkConnection,
                    private val catalogListUseCase: CatalogListUseCase
) : ViewModel() {

    private val _uiModel =
        MutableLiveData<List<Character>>()
    val uiModel: LiveData<List<Character>> = _uiModel

    private val _uiState = MutableLiveData<ScreenState>()
    val uiState: LiveData<ScreenState> = _uiState

    private var totalOffset = 0

    fun initialize() {
        loadData(0)
    }

    fun loadData(offset: Int) {
        totalOffset = offset
        _uiState.value = ScreenState.Loading
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        if (networkConnection.isNetworkConnected())
            setupObservers()
        else
            _uiState.setValue(ScreenState.Error)
    }

    private fun setupObservers() {
        observeResponse()

    }

    private fun observeResponse() {
        viewModelScope.launch {
            val response = catalogListUseCase.requestWithParameter(totalOffset.toString())
            response.data?.let {
                if (it.results.isNullOrEmpty()) displayError()
                else createAndPostUiModel(it.results)
            } ?: displayError()
        }
    }

    private fun displayError() {
        viewModelScope.launch {
            _uiState.setValue(ScreenState.Error)
        }
    }

    private fun createAndPostUiModel(response: List<Character>) {
        viewModelScope.launch {
            _uiModel.value = response
            _uiState.setValue(ScreenState.Success)
        }
    }

}