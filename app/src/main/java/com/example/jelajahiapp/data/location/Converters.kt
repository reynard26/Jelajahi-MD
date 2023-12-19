package com.example.jelajahiapp.data.location

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromLocation(location: Location): String {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun toLocation(locationString: String): Location {
        return Gson().fromJson(locationString, Location::class.java)
    }

    @TypeConverter
    fun fromPhotos(photos: List<Photo>?): String {
        return Gson().toJson(photos)
    }

    @TypeConverter
    fun toPhotos(photosString: String): List<Photo>? {
        val listType = object : TypeToken<List<Photo>?>() {}.type
        return Gson().fromJson(photosString, listType)
    }

    @TypeConverter
    fun fromTypes(types: List<String>): String {
        return Gson().toJson(types)
    }

    @TypeConverter
    fun toTypes(typesString: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(typesString, listType)
    }
}