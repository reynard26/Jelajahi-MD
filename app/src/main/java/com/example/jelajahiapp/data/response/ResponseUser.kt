package com.example.jelajahiapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null

)