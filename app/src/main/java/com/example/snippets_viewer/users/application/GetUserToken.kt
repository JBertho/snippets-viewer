package com.example.snippets_viewer.users.application

import android.content.Context
import android.content.SharedPreferences

class GetUserToken(private val context: Context) {

    private val preference: SharedPreferences
        get() = context.getSharedPreferences(
                "user",
                Context.MODE_PRIVATE
        )

    fun execute(): String? = preference.getString("token", null)

}