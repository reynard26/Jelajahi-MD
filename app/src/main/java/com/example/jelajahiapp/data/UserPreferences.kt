package com.example.jelajahiapp.data
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = UserPreferences.USER_PREF)
class UserPreferences(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
    }
    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .mapNotNull { preferences ->
                preferences[PreferencesKey.onBoardingKey]
            }
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    fun getToken(): Flow<String?> = flow {
        try {
            val preferences = dataStore.data.first()
            val token = preferences[TOKEN_KEY] ?: ""
            emit(token)
        } catch (e: Exception) {
            emit(null) //
        }
    }

    fun getId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID] ?: ""
        }
    }

    fun getEmail(): Flow<String?> = flow {
        try {
            val preferences = dataStore.data.first()
            val email = preferences[EMAIL] ?: ""
            emit(email)
        } catch (e: Exception) {
            emit(null) // Handle the error case
        }
    }

    suspend fun saveUserToken(token: String, userId: String, email: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[USER_ID] = userId
            preferences[EMAIL] = email
        }
    }


    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
            preferences[USER_ID] = ""
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val EMAIL = stringPreferencesKey("email")
        private val USER_ID = stringPreferencesKey("userId")
        const val USER_PREF = "user_prefs"
        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}