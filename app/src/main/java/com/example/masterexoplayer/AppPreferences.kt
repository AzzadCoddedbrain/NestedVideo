package com.example.masterexoplayer

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME = "SpinKotlin"
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }


}