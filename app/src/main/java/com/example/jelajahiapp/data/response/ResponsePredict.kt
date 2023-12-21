package com.example.jelajahiapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponsePredict(

    @field:SerializedName("predictResult")
    val predictResult: Predict? = null
    )

data class Predict(

    @field:SerializedName("tipe")
    val tipe: String,

    @field:SerializedName("province")
    val province: String,

    @field:SerializedName("origin")
    val origin: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("longitude")
    val longitude: String,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,


    )