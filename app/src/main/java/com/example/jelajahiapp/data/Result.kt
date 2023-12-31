package com.example.jelajahiapp.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String, val message: String? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}