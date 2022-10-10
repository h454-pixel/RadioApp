package com.example.radioapp.api

import retrofit2.Response
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.Model.ListCountry
import com.example.radioapp.Model.ListRecommed
import retrofit2.http.*

interface RadioService {

    @FormUrlEncoded
    @POST("api/country_list.php")
    suspend fun getCountryList(
        @Field("lc") lc: String,
        @Field("cc") cc: String,
        ): Response<ListCountry>

    @Headers("Content-Type: application/json")
    @POST("api/stations_list.php")
    suspend fun getRadioList(@Body radioRequest: RadioRequest): Response<ListRadio>



   // @Headers("Content-Type: application/json")
    @POST("api/recommended_list.php")
    suspend fun getlistrecommed(): Response<ListRecommed>


}