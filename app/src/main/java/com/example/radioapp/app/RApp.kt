package com.example.radioapp.app

import android.app.Application

import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RApp : Application() {

    override fun onCreate() {
        super.onCreate()


    }

}