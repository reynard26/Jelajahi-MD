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
                    // Handle errors if necessary
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

    private val locationsList = listOf("batiklasem", "batikparang", "batikpati", "batikpekalongan", "batiksidoluhur")
}