package com.example.jelajahiapp.data.retrofit

import com.example.jelajahiapp.data.location.ResponseLocation
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponsePredict
import com.example.jelajahiapp.data.response.ResponseUser
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ResponseUser>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseLogin>

    @POST("edit")
    suspend fun changepassword(@Body changeRequest: ChangeRequest): Response<ResponseUser>

    @POST("getExplore")
    suspend fun getExplore(@Body exploreRequest: ExploreRequest): Response<ResponseLocation>

    @GET("allpost")
    suspend fun getCommunity(): Response<ResponseBody>

    @PUT("editpost/{id}")
    suspend fun editCommunityPost(@Path("id") id: Long, communityRequestPost: CommunityRequestPost): Response<ResponseBody>




    @Multipart
    @POST("/")
    suspend fun recommendation(@Part file: MultipartBody.Part): Response<ResponsePredict>


    @POST("newpost")
    suspend fun addCommunityPost(@Body communityRequestPost: CommunityRequestPost): Response<ResponseUser>
}