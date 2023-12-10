package com.example.jelajahiapp.data

import android.util.Log
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



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
                Log.d("JelajahiRepository", "Attempting login for email: $email")
                val response = apiService.login(email, password)
                emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("JelajahiRepository", "Login failed: ${e.message}")
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun getToken(): Flow<String?> = userPreferences.getToken()

    suspend fun saveToken(token: String, userId: String) {
        userPreferences.saveUserToken(token, userId)
    }
    suspend fun logout() {
        userPreferences.logout()
    }
}
