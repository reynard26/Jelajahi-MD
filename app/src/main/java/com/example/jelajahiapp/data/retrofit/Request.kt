package com.example.jelajahiapp.data.retrofit

import com.example.jelajahiapp.data.response.ResponseCommunity

data class LoginRequest(val email: String, val password: String)

data class ChangeRequest(val email: String, val currentPassword: String, val newPassword: String)

data class RegisterRequest(val name: String, val email: String, val password: String)

data class ExploreRequest(val propertyName: String)

data class ResponseCommunityList(
    val communities: List<ResponseCommunity>
)