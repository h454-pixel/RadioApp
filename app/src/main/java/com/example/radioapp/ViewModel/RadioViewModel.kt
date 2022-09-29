package com.example.radioapp.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.radioapp.Model.ListRadio
import com.example.radioapp.Model.ListCountry
import com.example.radioapp.Model.ListRecommed

import com.example.radioapp.Repository.RadioRepository

import kotlinx.coroutines.flow.collect
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import  com.example.radioapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class RadioViewModel@Inject constructor (private val repository: RadioRepository, application: Application, ): AndroidViewModel(application){

    private val _getlistradio: MutableLiveData<NetworkResult<ListRadio>> = MutableLiveData()
    val getlistradio: LiveData<NetworkResult<ListRadio>> =   _getlistradio


    fun getRadiolist(
        cc_key: String,
        lc: String,
        c_code: String,
        curentpage: String,
    ) = viewModelScope.launch {
        repository.getRadiolist(cc_key,  lc,  c_code, curentpage)
    }


    private val _getlistcountry: MutableLiveData<NetworkResult<ListCountry>> = MutableLiveData()
    val getlistcountry: LiveData<NetworkResult<ListCountry>> =   _getlistcountry


    fun getcountrylist(
        cc: String,
        lc: String,
        ) = viewModelScope.launch {
        repository.getcountrylist(cc, lc)
          //  collect. { values -> _getlistcountry.value = values }
    }


    private val _getlistrecommed: MutableLiveData<NetworkResult<ListRecommed>> = MutableLiveData()
    val getlistrecommed: LiveData<NetworkResult<ListRecommed>> =   _getlistrecommed

    fun getlistrecommed() = viewModelScope.launch {
        repository.getlistrecommed()
        //  collect. { values -> _getlistcountry.value = values }
    }








}