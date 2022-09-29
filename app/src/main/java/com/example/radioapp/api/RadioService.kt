package com.example.radioapp.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.Model.ListCountry
import com.example.radioapp.Model.ListRecommed

interface RadioService {

    @FormUrlEncoded
    @POST("api/country_list.php?")
    suspend fun getCountryList(
        @Field("lc") lc: String,
        @Field("cc") cc: String,
        ): Response<ListCountry>

//http://radioly.app/api/stations_list.php?
    @FormUrlEncoded
    @POST("api/stations_list.php")
    suspend fun getRadioList(
        @Field("cc") cc_key: String,
        @Field("lc") lc: String,
        @Field("c_code") c_code: String,
        @Field("curentpage")currentpage: String,

    ): Response<ListRadio>



    @FormUrlEncoded
    @POST("api/recommended_list.php?")
    suspend fun getlistrecommed(): Response<ListRecommed>





}