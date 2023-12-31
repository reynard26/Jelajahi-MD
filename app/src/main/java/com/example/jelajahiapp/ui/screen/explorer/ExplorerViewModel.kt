package com.example.jelajahiapp.ui.screen.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExplorerViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _locations = MutableStateFlow<List<PlaceResult>>(emptyList())
    val locations: StateFlow<List<PlaceResult>> get() = _locations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var currentLocationIndex = 0

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _locationDetails = MutableStateFlow<PlaceResult?>(null)
    val locationDetails: StateFlow<PlaceResult?> get() = _locationDetails

    private val _isLoadingDetails = MutableStateFlow(false)
    val isLoadingDetails: StateFlow<Boolean> get() = _isLoadingDetails

    init {
        getExplore()
    }

    fun getExplore() {
        viewModelScope.launch {
            if (!_isLoading.value) {
                try {
                    _isLoading.value = true
                    if (currentLocationIndex < locationsList.size) {
                        val location = locationsList[currentLocationIndex]
                        val exploreRequest = ExploreRequest(propertyName = location)
                        val response = repository.getExplore(exploreRequest)

                        if (response.isSuccessful) {
                            val results = response.body()?.results ?: emptyList()
                            val combinedList = _locations.value + results
                            _locations.value = combinedList.distinctBy { it.placeId }
                            currentLocationIndex++
                        }
                    }
                } catch (e: Exception) {
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val filteredLocations: StateFlow<List<PlaceResult>> = combine(locations, searchQuery) { locations, query ->
        if (query.isBlank()) {
            locations
        } else {
            locations.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun getLocationDetails(placeId: String) {
        viewModelScope.launch {
            try {
                _isLoadingDetails.value = true
                if (_locations.value.isEmpty()) {
                    getExploreForAllLocations()
                }

                _isLoading.collect { isLoading ->
                    if (!isLoading) {
                        val location = _locations.value.find { it.placeId == placeId }

                        if (location != null) {
                            _locationDetails.value = location
                        }

                        _isLoadingDetails.value = false
                    }
                }
            } catch (e: Exception) {
                // Handle the exception
                _isLoadingDetails.value = false
            }
        }
    }

    private fun getExploreForAllLocations() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                locationsList.forEach { location ->
                    val exploreRequest = ExploreRequest(propertyName = location)
                    val response = repository.getExplore(exploreRequest)

                    if (response.isSuccessful) {
                        val results = response.body()?.results ?: emptyList()
                        val combinedList = _locations.value + results
                        _locations.value = combinedList.distinctBy { it.placeId }
                    }
                }
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _favoritePlaceStatus = MutableStateFlow(false)
    val favoritePlaceStatus: StateFlow<Boolean>
        get() = _favoritePlaceStatus

    fun updateStatus(placeId: String) = viewModelScope.launch {
        _favoritePlaceStatus.value = repository.isFavoritePlaces(placeId).first()
    }

    fun changeFavorite(favorite: PlaceResult) {
        viewModelScope.launch {
            if (_favoritePlaceStatus.value) {
                repository.delete(favorite)
            } else {
                repository.save(favorite)
            }

            _favoritePlaceStatus.value = !_favoritePlaceStatus.value
        }
    }

    private val locationsList = listOf("batiklasem", "batikparang", "batikpati", "batikpekalongan", "batiksidoluhur")
}