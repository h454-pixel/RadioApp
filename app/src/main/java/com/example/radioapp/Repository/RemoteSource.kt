package com.example.radioapp.Repository

import androidx.lifecycle.LiveData
import com.example.radioapp.api.RadioService
import javax.inject.Inject


class RemoteSource @Inject constructor(private val radio: RadioService) {
    //val readAllData: LiveData<List<ListRadio>> = radio.readAllData()

    suspend fun getRadiolist(
        cc_key: String,
        lc: String,
        c_code: String,
        curentpage: String,
    ) = radio.getRadioList(cc_key, lc, c_code, curentpage)


    suspend fun getcountrylist(
        lc:String,
        cc:String,
    ) =radio.getCountryList(lc,cc)


    suspend fun getlistrecommed() =radio.getlistrecommed()





}