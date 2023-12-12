package com.example.jelajahiapp.data

import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.response.ResponseUser
import com.example.jelajahiapp.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class JelajahiRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
) {
    suspend fun login(
        email: String,
        password: String,
    ): Flow<Result<ResponseLogin>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = apiService.login(email, password)
                emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.message.toString()))
            }
        }
    }
    suspend fun register(
        name: String, email: String, password: String,
    ): Flow<Result<ResponseUser>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getToken(): Flow<String?> = userPreferences.getToken()

    suspend fun saveToken(token: String, userId: String) {
        userPreferences.saveUserToken(token, userId)
    }
    suspend fun logout() {
        userPreferences.logout()
    }
}
