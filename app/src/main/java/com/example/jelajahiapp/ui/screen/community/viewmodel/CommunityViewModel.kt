package com.example.jelajahiapp.ui.screen.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.response.ResponseCommunity
import com.example.jelajahiapp.data.response.ResponseUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response


class CommunityViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _defaultState = MutableStateFlow<Result<ResponseUser>>(Result.Loading)
    val defaultState: StateFlow<Result<ResponseUser>> get() = _defaultState

    private val _communityList = MutableStateFlow<List<ResponseCommunity>>(emptyList())
    val communityList: StateFlow<List<ResponseCommunity>> get() = _communityList


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun getToken(): LiveData<String?> = repository.getToken().asLiveData()

    fun getCommunity() = viewModelScope.launch {
        _isLoading.value = true
        try {
            val response: Response<ResponseBody> = repository.getCommunity()
            if (response.isSuccessful) {
                val responseBodyString = response.body()?.string() ?: ""
                val gson = Gson()
                val communityList: List<ResponseCommunity> = gson.fromJson(responseBodyString, object : TypeToken<List<ResponseCommunity>>() {}.type)
                _communityList.value = communityList
            } else {
                Log.e("CommunityViewModel", "Error getting community data: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("CommunityViewModel", "Error getting community data: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    fun getId(): LiveData<String?> = repository.getId().asLiveData()

    fun addCommunityPost(place_name :String, location: String, description: String) {
        viewModelScope.launch {
            repository.addCommunityPost(place_name, location, description).collect {
                _defaultState.value = it as Result<ResponseUser>
            }
        }
    }

    fun editCommunityPost(id: Long, place_name: String, description: String, location: String) {
        viewModelScope.launch {
            repository.editCommunityPost(id, place_name, description, location).collect {
                _defaultState.value = it as Result<ResponseUser>
            }
        }
    }

    private val _selectedCommunity = MutableStateFlow<ResponseCommunity?>(null)
    val selectedCommunity: StateFlow<ResponseCommunity?> get() = _selectedCommunity

    fun selectCommunityForEdit(community: ResponseCommunity) {
        _selectedCommunity.value = community
    }
}