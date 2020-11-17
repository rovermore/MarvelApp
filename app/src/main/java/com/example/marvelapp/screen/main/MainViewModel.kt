package com.example.marvelapp.screen.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelapp.model.Character
import com.example.marvelapp.usecase.CatalogListUseCase
import com.example.marvelapp.utils.NetworkConnection
import com.example.marvelapp.utils.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainViewModel
@Inject constructor(private val networkConnection: NetworkConnection,
                    private val catalogListUseCase: CatalogListUseCase
) : ViewModel() {

    private val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Main
    private val viewModelScope = CoroutineScope(coroutineContext)

    private val _uiModel =
        MutableLiveData<List<Character>>()
    val uiModel: LiveData<List<Character>> = _uiModel

    private val _uiState = MutableLiveData<ScreenState>()
    val uiState: LiveData<ScreenState> = _uiState

    private var totalOffset = 0

    fun initialize(offset: Int) {
        loadData(offset)
    }

    fun loadData(offset: Int) {
        totalOffset = offset
        _uiState.setValue(ScreenState.Loading)
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
            if (response.data == null) {
                displayError()
            } else if (response.data.results.isNullOrEmpty()) {
                displayError()
            } else {
                createAndPostUiModel(response.data.results)
            }
        }

    }

    private fun displayError() {
        _uiState.setValue(ScreenState.Error)
    }

    private fun createAndPostUiModel(response: List<Character>) {
        viewModelScope.launch {
            _uiModel.setValue(response)
            _uiState.setValue(ScreenState.Success)
        }
    }

}