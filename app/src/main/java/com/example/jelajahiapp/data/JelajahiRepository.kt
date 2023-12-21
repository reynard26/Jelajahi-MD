package com.example.jelajahiapp.data

import com.example.jelajahiapp.data.location.PlaceResult
import com.example.jelajahiapp.data.location.ResponseLocation
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponsePredict
import com.example.jelajahiapp.data.response.ResponseUser
import com.example.jelajahiapp.data.retrofit.ApiService
import com.example.jelajahiapp.data.retrofit.ChangeRequest
import com.example.jelajahiapp.data.retrofit.ExploreRequest
import com.example.jelajahiapp.data.retrofit.LoginRequest
import com.example.jelajahiapp.data.retrofit.RegisterRequest
import com.example.jelajahiapp.data.room.Cultural
import com.example.jelajahiapp.data.room.FakeCulturalDataSource
import com.example.jelajahiapp.data.room.FavoriteLocationDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


class JelajahiRepository(
    private val apiService: ApiService,
    private val apiServiceRecommendation: ApiService,
    private val userPreferences: UserPreferences,
    private val dataRoom: FavoriteLocationDatabase
) {
    suspend fun login(
        email: String,
        password: String,
    ): Flow<Result<ResponseLogin?>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    emit(Result.Success(response.body()))
                } else {
                    val errorMessage = response.errorBody()?.string()
                    try {
                        val json = JSONObject(errorMessage.toString())
                        val serverMsg = json.getString("msg")
                        emit(Result.Error(serverMsg, errorMessage))
                    } catch (e: JSONException) {
                        // Handle JSON parsing error, show a default message, or take appropriate action
                        emit(Result.Error("An error occurred", errorMessage))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.message.toString()))
            }
        }
    }
    fun register(
        name: String, email: String, password: String,
    ): Flow<Result<ResponseUser?>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(RegisterRequest(name,email, password))

            if (response.isSuccessful) {
                emit(Result.Success(response.body()))
            } else {
                val errorMessage = response.errorBody()?.string()
                try {
                    val json = JSONObject(errorMessage)
                    val message = json.getString("message")
                    emit(Result.Error(message, errorMessage))
                } catch (e: Exception) {
                    emit(Result.Error("An error occurred", errorMessage))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun changePassword(
        email:String,
        currentPassword: String,
        newPassword: String,
    ): Flow<Result<ResponseUser?>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.changepassword(ChangeRequest(email, currentPassword, newPassword))
                if (response.isSuccessful) {
                    emit(Result.Success(response.body()))
                } else {
                    val errorMessage = response.errorBody()?.string()
                    try {
                        val json = JSONObject(errorMessage.toString())
                        val serverMsg = json.getString("msg")
                        emit(Result.Error(serverMsg, errorMessage))
                    } catch (e: JSONException) {
                        // Handle JSON parsing error, show a default message, or take appropriate action
                        emit(Result.Error("An error occurred", errorMessage))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun getToken(): Flow<String?> = userPreferences.getToken()
    fun getId(): Flow<String?> = userPreferences.getId()

    fun getEmail(): Flow<String?> = userPreferences.getEmail()

    suspend fun getExplore(exploreRequest: ExploreRequest): Response<ResponseLocation> {
        return apiService.getExplore(exploreRequest)
    }

    suspend fun getCommunity(): Response<ResponseBody> = apiService.getCommunity()

    suspend fun saveToken(token: String, userId: String, email: String) {
        userPreferences.saveUserToken(token, userId, email)
    }

    //LOCAL FOR CULTURAL
    private val listCultural = mutableListOf<Cultural>()

    init {
        if (listCultural.isEmpty()) {
            FakeCulturalDataSource.dummyCultural.forEach { cultural ->
                listCultural.add(
                    Cultural(
                        id = cultural.id,
                        image = cultural.image,
                        culturalName = cultural.culturalName,
                        culturalType = cultural.culturalType,
                        location = cultural.location,
                        description = cultural.description,
                    )
                )
            }
        }
    }

    fun getallCultural(): Flow<List<Cultural>> {
        return flowOf(listCultural)
    }

    fun getCulturaById(culturalId: Long): Cultural {
        return listCultural.first { cultural ->
            cultural.id == culturalId
        }
    }
    suspend fun logout() {
        userPreferences.logout()
    }


    suspend fun save(favorite: PlaceResult) {
        withContext(Dispatchers.IO) {
            dataRoom.favoriteLocationDao().insert(favorite)
        }
    }

    suspend fun delete(favorite: PlaceResult) {
        withContext(Dispatchers.IO) {
            dataRoom.favoriteLocationDao().delete(favorite)
        }
    }

    fun getAllFavoritePlaces(): Flow<List<PlaceResult>> {
        return dataRoom.favoriteLocationDao().getAllFavoritePlaces()
    }

    fun isFavoritePlaces(placeId: String): Flow<Boolean> {
        return dataRoom.favoriteLocationDao().allFavoritePlaces(placeId)
    }

    fun recommendation(file: MultipartBody.Part): Flow<Result<ResponsePredict?>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiServiceRecommendation.recommendation(file)

                if (response.isSuccessful) {
                    emit(Result.Success(response.body()))
                } else {
                    val errorMessage = response.errorBody()?.string()
                    emit(Result.Error("error", errorMessage))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}
