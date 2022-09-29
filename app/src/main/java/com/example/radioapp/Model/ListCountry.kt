package com.example.radioapp.Model

data class ListCountry(
    val data: List<Country>,
    val message: String,
    val success: Int
) {
    data class Country(
        val c_lang: String,
        val c_name: String,
        val cc: String,
        val image: String,
        val total_radio_stations: String
    )
}