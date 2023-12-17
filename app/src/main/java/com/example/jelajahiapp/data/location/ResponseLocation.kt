package com.example.jelajahiapp.data.location

import com.google.gson.annotations.SerializedName

data class ResponseLocation(

    @field:SerializedName("results")
    val results: List<PlaceResult>,

    @field:SerializedName("html_attributions")
    val htmlAttributions: List<String>,
    )

data class PlaceResult(

    @field:SerializedName("geometry")
    val geometry: Location,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photos")
    val photos: List<Photo>?,

    @field:SerializedName("place_id")
    val placeId: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("types")
    val types: List<String>,

    @field:SerializedName("user_ratings_total")
    val userRatingsTotal: Int,

    @field:SerializedName("vicinity")
    val vicinity: String,
)

data class Location(
    @field:SerializedName("location")
    val location: LatLong
)
data class LatLong(
    @field:SerializedName("lat")
    val lat: Double,
    @field:SerializedName("lng")
    val lng: Double
)

data class Photo(
    @field:SerializedName("height")
    val height: Int,
    @field:SerializedName("html_attributions")
    val htmlAttributions: List<String>,
    @field:SerializedName("photo_reference")
    val photoReference: String,
    @field:SerializedName("width")
    val width: Int
)