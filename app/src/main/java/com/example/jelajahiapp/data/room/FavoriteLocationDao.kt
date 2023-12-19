package com.example.jelajahiapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jelajahiapp.data.location.PlaceResult
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: PlaceResult)

    @Delete
    suspend fun delete(favorite: PlaceResult)

    @Query("SELECT * FROM locations")
    fun getAllFavoritePlaces(): Flow<List<PlaceResult>>

    @Query("SELECT EXISTS(SELECT * FROM locations WHERE placeId = :placeId)")
    fun allFavoritePlaces(placeId: String): Flow<Boolean>

}