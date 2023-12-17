package com.example.jelajahiapp.ui.screen.explorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.data.location.ResponseLocation
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

class ExplorerViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _responseLocation = MutableLiveData<Response<ResponseLocation>>()
    val responseLocation: LiveData<Response<ResponseLocation>> get() = _responseLocation

    private val _filteredLocations = MutableStateFlow<List<PlaceResult>>(emptyList())
    val filteredLocations: StateFlow<List<PlaceResult>> get() = _filteredLocations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var currentLocationIndex = 0

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    init {
        // Start observing the search query and update filtered locations accordingly
        viewModelScope.launch {
            _searchQuery.collectLatest { query ->
                filterLocations(query)
            }
        }

        // Call getExplore in the init block
        getExplore()
    }

    fun getExplore() {
        viewModelScope.launch {
            if (!_isLoading.value) {
                try {
                    _isLoading.value = true
                    if (currentLocationIndex < locations.size) {
                        val location = locations[currentLocationIndex]
                        val exploreRequest = ExploreRequest(propertyName = location)
                        val response = repository.getExplore(exploreRequest)

                        if (response.isSuccessful) {
                            val results = response.body()?.results ?: emptyList()
                            _filteredLocations.value = _filteredLocations.value + results
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

    fun filterLocations(query: String) {
        val locations = _filteredLocations.value

        val filteredList = if (query.isNotBlank()) {
            locations.filter { it.name.contains(query, ignoreCase = true) }
        } else {
            locations
        }

        _filteredLocations.value = filteredList
    }

    companion object {
        private val locations = listOf("batiklasem", "batikparang", "batikpati", "batikpekalongan", "batiksidoluhur")
    }
}