package com.example.radioapp.Model

data class ListRadio(
    val curentpage: Int,
    val `data`: List<RadioChannel>,
    val limit: Int,
    val message: String,
    val state_data: List<StateData>,
    val success: Int,
    val totaldata: Int,
    val totalpages: Int
){
    data class StateData(
        val name: String
    )
    data class RadioChannel(
        val country_name: String,
        val genre: String,
        val image: String,
        val name: String,
        val region: String,
        val st_id: String,
        val st_link: String
        )
}

