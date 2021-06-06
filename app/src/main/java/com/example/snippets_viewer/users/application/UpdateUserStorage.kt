package com.example.snippets_viewer.users.application

import android.content.Context
import android.content.SharedPreferences
import com.example.snippets_viewer.users.infrastructure.api.models.ConnectedUserResponse

class UpdateUserStorage(private val context: Context) {


    private val preference: SharedPreferences
        get() = context.getSharedPreferences(
            "user",
            Context.MODE_PRIVATE
        )

    fun execute(user: ConnectedUserResponse) {
        with(preference.edit()) {
            putString("token", user.token)
            putLong("userId", user.id)
            putString("mail", user.email)
            putString("username", user.username)
            apply()
        }
    }



}