package com.example.radioapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {

    val callApi = MutableLiveData<Boolean>()

    fun select(item: Boolean) {
        callApi.value = item
    }
}