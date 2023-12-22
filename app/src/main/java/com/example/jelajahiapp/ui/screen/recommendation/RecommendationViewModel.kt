package com.example.jelajahiapp.ui.screen.recommendation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.data.response.ResponsePredict
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import com.example.jelajahiapp.data.room.Cultural
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class RecommendationViewModel(private val repository: JelajahiRepository) : ViewModel(){

    private val _recommendationState = MutableStateFlow<Result<ResponsePredict>>(Result.Loading)
    val recommendationState: StateFlow<Result<ResponsePredict>> get() = _recommendationState


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _locations = MutableStateFlow<List<PlaceResult>>(emptyList())
    val locations: StateFlow<List<PlaceResult>> get() = _locations

    fun recommendation(file: MultipartBody.Part, onUploadSuccess: () -> Unit, onUploadError: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("hasildariview", "Before emitting state")
                repository.recommendation(file)
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _recommendationState.value = result as Result<ResponsePredict>
                                onUploadSuccess()
                            }
                            is Result.Error -> {
                                _recommendationState.value = result
                                result.error?.let {
                                    onUploadError()
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

    private val _uiState: MutableStateFlow<Result<Cultural>> =
        MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<Cultural>>
        get() = _uiState

    fun getCulturalName(cultunalName: String) {
        viewModelScope.launch {
            try {
                _uiState.value = Result.Loading
                _uiState.value = Result.Success(repository.getCulturalByName(cultunalName))
            } catch (e: Exception) {
                _uiState.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }


    fun getRecommendationList(locationsList: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                    val exploreRequest = ExploreRequest(propertyName = locationsList)
                    val response = repository.getExplore(exploreRequest)

                    if (response.isSuccessful) {
                        val results = response.body()?.results ?: emptyList()
                        val combinedList = _locations.value + results
                        _locations.value = combinedList.distinctBy { it.placeId }
                    }

            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val locationsList = listOf("batiklasem", "batikparang", "batikpati", "batikpekalongan", "batiksidoluhur")
}