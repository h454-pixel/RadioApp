package com.example.radioapp.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ListRadio(
    val curentpage: Int,
    @Expose
    @SerializedName("data")
    val data: List<RadioChannel>,
    val limit: Int,
    val message: String,
    val state_data: List<StateData>,
    val success: Int,
    val totaldata: Int,
    val totalpages: Int
):Serializable{
    data class StateData(
        @Expose
        @SerializedName("name")
        val name: String
    )

    data class RadioChannel(
        @Expose
        @SerializedName("country_name")
        val country_name: String,
        @Expose
        @SerializedName("genre")
        val genre: String,
        @Expose
        @SerializedName("image")
        val image: String,
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("region")
        val region: String,
        @Expose
        @SerializedName("st_id")
        val st_id: String,
        @Expose
        @SerializedName("st_link")
        val st_link: String
        )
}

