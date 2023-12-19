package com.example.jelajahiapp.ui.screen.cultural

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.room.Cultural
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailCulturalViewModel(private val repository: JelajahiRepository) : ViewModel() {


    private val _uiState: MutableStateFlow<Result<Cultural>> =
        MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<Cultural>>
        get() = _uiState

    fun getCulturalById(culturalId: Long) {
        viewModelScope.launch {
            try {
                _uiState.value = Result.Loading
                _uiState.value = Result.Success(repository.getCulturaById(culturalId))
            } catch (e: Exception) {
                _uiState.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}