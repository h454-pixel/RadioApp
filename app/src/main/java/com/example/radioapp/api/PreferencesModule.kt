package com.example.radioapp.api

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@SuppressLint("StaticFieldLeak")
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    var context:Context? = null
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    /**
     * Provides Preferences object with MODE_PRIVATE
     */
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("shareit", Context.MODE_PRIVATE)

    fun init(context: Context){
        PreferencesModule.context = context
        preferences = context.getSharedPreferences("shareiit", Context.MODE_PRIVATE)
        editor = preferences!!.edit()
    }

    fun write(name:String,value:Any){
        when (value) {
            is String -> {
                editor!!.putString(name,value).commit()
            }
            is Int -> {
                editor!!.putInt(name,value).commit()
            }
            is Float -> {
                editor!!.putFloat(name,value).commit()
            }
            is Boolean -> {
                editor!!.putBoolean(name,value).commit()
            }
            is Long -> {
                editor!!.putLong(name,value).commit()
            }
        }
    }

    fun read(name:String):Any?{
        val keys = preferences!!.all

        if (keys != null){
            for (entry in keys){
                if (entry.key == name){
                    return entry.value
                }
            }
        }
        return null
    }

}
