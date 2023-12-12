package com.example.jelajahiapp.ui.screen.authorization.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: JelajahiRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<ResponseLogin>>(Result.Loading)
    val loginState: StateFlow<Result<ResponseLogin>> get() = _loginState

    private val _defaultState = MutableStateFlow<Result<ResponseUser>>(Result.Loading)
    val defaultState: StateFlow<Result<ResponseUser>> get() = _defaultState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect {
                _loginState.value = it as Result<ResponseLogin>
            }
        }
    }

    fun signup(userName :String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(userName, email, password).collect {
                _defaultState.value = it as Result<ResponseUser>
            }
        }
    }

    fun saveToken(token: String, userId: String) {
        viewModelScope.launch {
            repository.saveToken(token, userId)
        }
    }

    fun getToken(): LiveData<String?> = repository.getToken().asLiveData()
}