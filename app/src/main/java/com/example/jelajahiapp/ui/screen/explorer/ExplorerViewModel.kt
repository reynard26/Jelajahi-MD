package com.example.jelajahiapp.ui.screen.explorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.location.ResponseLocation
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import kotlinx.coroutines.launch
import retrofit2.Response

class ExplorerViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _responseLocation = MutableLiveData<Response<ResponseLocation>>()
    val responseLocation: LiveData<Response<ResponseLocation>> get() = _responseLocation

    fun getExplore(query: String) {
        val exploreRequest = ExploreRequest(propertyName = query)
        viewModelScope.launch {
            _responseLocation.value = repository.getExplore(exploreRequest)
        }
    }
}