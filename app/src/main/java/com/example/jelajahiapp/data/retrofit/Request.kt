package com.example.jelajahiapp.data.retrofit

data class LoginRequest(val email: String, val password: String)

data class ChangeRequest(val email: String, val currentPassword: String, val newPassword: String)

data class RegisterRequest(val name: String, val email: String, val password: String)

data class ExploreRequest(val propertyName: String)