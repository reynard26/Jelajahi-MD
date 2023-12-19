package com.example.jelajahiapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jelajahiapp.data.location.PlaceResult

@Database(
    entities = [PlaceResult::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteLocationDatabase : RoomDatabase() {

    abstract fun favoriteLocationDao(): FavoriteLocationDao

    companion object {
        @Volatile
        var INSTANCE: FavoriteLocationDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteLocationDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteLocationDatabase::class.java, "favorite_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}