package com.example.radioapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


data class ListRecommed(
    val success: Int,
    val message: String,
    val `data`: List<Recommed>,
){

    data class Recommed(
        val stId: String,
        val name: String,
        val image: String,
        val genre: String,
        val region: String,
        val stLink: String,
        val countryName: String
    )



    }




