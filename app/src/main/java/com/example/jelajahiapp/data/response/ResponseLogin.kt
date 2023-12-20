package com.example.jelajahiapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

    @field:SerializedName("loginResult")
    val loginResult: User,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("msg")
    val message: String,

    @field:SerializedName("user")
    val user: User,

    )

data class User(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("accessToken")
    val token: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,


)
