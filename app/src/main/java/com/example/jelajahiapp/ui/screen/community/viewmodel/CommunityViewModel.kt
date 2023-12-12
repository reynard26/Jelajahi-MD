package com.example.jelajahiapp.ui.screen.community.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.response.ResponseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody


class CommunityViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _defaultState = MutableStateFlow<Result<ResponseUser>>(Result.Loading)
    val defaultState: StateFlow<Result<ResponseUser>> get() = _defaultState


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