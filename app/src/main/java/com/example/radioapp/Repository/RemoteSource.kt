package com.example.radioapp.Repository

import com.example.radioapp.api.RadioService
import com.example.radioapp.api.RadioRequest


import javax.inject.Inject


class RemoteSource @Inject constructor(private val radio: RadioService) {
    //val readAllData: LiveData<List<ListRadio>> = radio.readAllData()

    suspend fun getRadiolist(
request:RadioRequest
    ) = radio.getRadioList(request)


    suspend fun getcountrylist(
        lc:String,
        cc:String,
    ) =radio.getCountryList(lc,cc)


    suspend fun getlistrecommed() =radio.getlistrecommed()





}