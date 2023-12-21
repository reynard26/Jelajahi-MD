package com.example.jelajahiapp.ui.screen.recommendation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.response.ResponsePredict
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class RecommendationViewModel(private val repository: JelajahiRepository) : ViewModel(){

    private val _recommendationState = MutableStateFlow<Result<ResponsePredict>>(Result.Loading)
    val recommendationState: StateFlow<Result<ResponsePredict>> get() = _recommendationState


    fun recommendation(file: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                Log.d("hasildariview", "Before emitting state")
                repository.recommendation(file)
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _recommendationState.value = result as Result<ResponsePredict>
                                Log.d("hasildariview", result.data.toString())
                            }
                            is Result.Error -> {
                                _recommendationState.value = result
                                result.error?.let {
                                }
                            }
                            Result.Loading -> {
                            }
                        }
                    }
            } catch (e: Exception) {
            }
        }
    }
}