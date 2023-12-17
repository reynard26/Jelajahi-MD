package com.example.jelajahiapp.data.retrofit

import com.example.jelajahiapp.data.location.ResponseLocation
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponseUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ResponseUser>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseLogin>

    @POST("getExplore")
    suspend fun getExplore(@Body exploreRequest: ExploreRequest): Response<ResponseLocation>

}