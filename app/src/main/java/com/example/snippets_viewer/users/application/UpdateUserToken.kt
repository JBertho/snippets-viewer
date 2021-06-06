package com.example.snippets_viewer.users.application

import android.content.Context
import android.content.SharedPreferences

class UpdateUserToken(private val context: Context) {


    private val preference: SharedPreferences
        get() = context.getSharedPreferences(
            "user",
            Context.MODE_PRIVATE
        )

    fun execute(token: String) {
        with(preference.edit()) {
            putString("token", token)
            apply()
        }
    }



}