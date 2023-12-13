package com.example.jelajahiapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultural_table")
data class Cultural(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val image: Int,
    val culturalName: String,
    val culturalType: String,
    val location: String,
    val description: String,
    )