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
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    fun addCommunity(
        token: String,
        imageFile: MultipartBody.Part,
        description: RequestBody,
        lat: Float?,
        lon: Float?
    ) {
//        viewModelScope.launch {
//            repository.login(email, password).collect {
//                _defaultState.value = it as Result<ResponseLogin>
//            }
//        }
    }
}