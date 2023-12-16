package com.example.jelajahiapp.data

import android.util.Log
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponseUser
import com.example.jelajahiapp.data.retrofit.ApiService
import com.example.jelajahiapp.data.room.Cultural
import com.example.jelajahiapp.data.room.FakeCulturalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject


class JelajahiRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
) {
    suspend fun login(
        email: String,
        password: String,
    ): Flow<Result<ResponseLogin?>> {
        return flow {
            emit(Result.Loading)
            try {
                Log.d("dataapa aja2",email)
                Log.d("dataapa aja2",password)
                val response = apiService.login(email, password)
                Log.d("dataapa aja",response.toString())
                Log.d("dataapa aja",email)
                Log.d("dataapa aja",password)
                if (response.isSuccessful) {
                    emit(Result.Success(response.body()))
                } else {
                    val errorMessage = response.errorBody()?.string()
                    try {
                        val json = JSONObject(errorMessage.toString())
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
        }
    }
    fun register(
        name: String, email: String, password: String,
    ): Flow<Result<ResponseUser?>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)

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

    fun getToken(): Flow<String?> = userPreferences.getToken()
    fun getId(): Flow<String?> = userPreferences.getId()

    suspend fun saveToken(token: String, userId: String) {
        userPreferences.saveUserToken(token, userId)
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
}
