package com.example.jelajahiapp.ui.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import com.example.jelajahiapp.data.room.Cultural
import kotlinx.coroutines.delay
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

    private val _location = MutableStateFlow<PlaceResult?>(null)
    val location: StateFlow<PlaceResult?> get() = _location

    private val _locations = MutableStateFlow<List<PlaceResult>>(emptyList())
    val locations: StateFlow<List<PlaceResult>> get() = _locations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _locationDetails = MutableStateFlow<PlaceResult?>(null)
    val locationDetails: StateFlow<PlaceResult?> get() = _locationDetails

    private val _isLoadingDetails = MutableStateFlow(false)
    val isLoadingDetails: StateFlow<Boolean> get() = _isLoadingDetails


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

    fun setLogout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onLogoutSuccess.invoke()
        }
    }

    init {
        getRandomLocation()
    }

    fun getRandomLocation() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                if (_locations.value.isEmpty()) {
                    if (locationsList.isNotEmpty()) {
                        val location = locationsList[2]
                        val exploreRequest = ExploreRequest(propertyName = location)
                        val response = repository.getExplore(exploreRequest)

                        if (response.isSuccessful) {
                            val newLocations = response.body()?.results ?: emptyList()
                            if (newLocations != _locations.value) {
                                _locations.value = newLocations
                            }
                        }
                    }
                }

            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun showLocationDetails(placeId: String) {
        viewModelScope.launch {
            _isLoadingDetails.value = true

            while (_isLoading.value) {
                delay(100)
            }

            val location = _locations.value.find { it.placeId == placeId }
            if (location == null) {
                if (_locations.value.isEmpty()) {
                    getRandomLocation()
                }
            } else {
                Log.d("loksinyamana", location.toString())
                _locationDetails.value = location
            }

            _isLoadingDetails.value = false
        }
    }


    companion object {
        private val locationsList = listOf("batiklasem", "batikparang", "batikpati", "batikpekalongan", "batiksidoluhur")
    }
}