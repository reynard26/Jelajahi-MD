package com.example.jelajahiapp.data.retrofit

import com.example.jelajahiapp.data.location.ResponseLocation
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponseUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ResponseUser>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseLogin>

    @POST("getExplore")
    suspend fun getExplore(@Body exploreRequest: ExploreRequest): Response<ResponseLocation>

    @Multipart
    @POST("addPost")
    suspend fun createCommunityPost(
        @Header("Authorization") auth: String,
        @Part("placeName") title: String,
        @Part("location") name: String,
        @Part("userId") userId: String,
        @Part("description") latitude: RequestBody,
        @Part file: MultipartBody.Part,
    ): Response<ResponseLogin> //belum diubah
}