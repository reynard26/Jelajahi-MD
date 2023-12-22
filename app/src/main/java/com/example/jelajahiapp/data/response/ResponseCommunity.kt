package com.example.jelajahiapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseCommunity(

    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("placeName")
    val placeName: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("description")
    val description: String? = null
)