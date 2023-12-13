package com.example.jelajahiapp.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.room.Cultural
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _tokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> = _tokenFlow

    private val _uiState: MutableStateFlow<Result<List<Cultural>>> =
        MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<List<Cultural>>>
        get() = _uiState

    fun getToken(): LiveData<String?> = repository.getToken().asLiveData()
    fun fetchToken() {
        viewModelScope.launch {
            repository.getToken().collect {
                _tokenFlow.value = it
            }
        }
    }

    fun getAllCultural() {
        viewModelScope.launch {
            repository.getallCultural()
                .catch {
                    _uiState.value = Result.Error(it.message.toString())
                }

                .collect { listDog ->
                    _uiState.value = Result.Success(listDog)
                }
        }
    }

}