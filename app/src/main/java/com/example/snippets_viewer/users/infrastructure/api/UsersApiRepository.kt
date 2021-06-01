package com.example.snippets_viewer.users.infrastructure.api

import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.users.infrastructure.api.models.ConnectedUserResponse
import com.example.snippets_viewer.users.infrastructure.api.models.LoginRequest
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsersApiRepository {
    private var userService: UsersService? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        userService = retrofit.create(UsersService::class.java)
    }

    fun signIn(loginRequest: LoginRequest ,callback: Callback<ConnectedUserResponse>)
    {
        val call = userService?.signIn(loginRequest)
        call?.enqueue(callback)
    }
}