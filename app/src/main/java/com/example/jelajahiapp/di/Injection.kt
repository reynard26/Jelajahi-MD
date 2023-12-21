package com.example.jelajahiapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.jelajahiapp.data.JelajahiRepository
import com.example.jelajahiapp.data.UserPreferences
import com.example.jelajahiapp.data.retrofit.ApiConfig
import com.example.jelajahiapp.data.room.FavoriteLocationDatabase

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun getRepository(context: Context): JelajahiRepository {
        return JelajahiRepository(
            ApiConfig.getApiService(),
            ApiConfig.getApiRecommendation(),
            UserPreferences.getInstance(dataStore = context.dataStore),
            dataRoom = FavoriteLocationDatabase.getDatabase(context)
        )
    }
}