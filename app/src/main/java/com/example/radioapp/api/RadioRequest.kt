package com.example.radioapp.api

import com.google.gson.annotations.SerializedName

data class RadioRequest(
    @SerializedName("cc")
    val cc: String,
    @SerializedName("lc")
    val lc: String,
    @SerializedName("c_code")
    val c_code: String,
    @SerializedName("curentpage")
    val curentpage: String
)