package com.example.marvelapp.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.model.Character
import com.example.marvelapp.usecase.DetailUseCase
import com.example.marvelapp.utils.NetworkConnection
import com.example.marvelapp.utils.ScreenState
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel
    @Inject constructor(private val networkConnection: NetworkConnection,
                        private val detailUseCase: DetailUseCase
    ) : ViewModel() {

    private val _uiModel =
        MutableLiveData<Character>()
    val uiModel: LiveData<Character> = _uiModel

    private val _uiState = MutableLiveData<ScreenState>()
    val uiState: LiveData<ScreenState> = _uiState

    private var characterId: String = ""

    fun initialize(id: String?) {
        id?.let { this.characterId = it }
        loadData()
    }

    fun loadData() {
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
            val response = detailUseCase.requestWithParameter(characterId)
            if (response.data == null) {
                displayError()
            } else if (response.data.results.isNullOrEmpty()) {
                displayError()
            } else
                createAndPostUiModel(response.data.results[0])
        }
    }

    private fun displayError() {
        _uiState.value = ScreenState.Error
    }

    private fun createAndPostUiModel(response: Character) {
        viewModelScope.launch {
            _uiModel.value = response
            _uiState.setValue(ScreenState.Success)
        }
    }

}