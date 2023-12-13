package com.example.jelajahiapp.ui.screen.splash

import android.content.Context
import com.example.jelajahiapp.data.UserPreferences
import com.example.jelajahiapp.data.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InjectionBoarding {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context.dataStore)
    }


}
