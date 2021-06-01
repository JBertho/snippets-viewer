package com.example.snippets_viewer.users.infrastructure.api

import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.users.infrastructure.api.models.ConnectedUserResponse
import com.example.snippets_viewer.users.infrastructure.api.models.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersService {

    @POST("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Auth}signin")
    fun signIn(@Body loginRequest: LoginRequest): Call<ConnectedUserResponse>
}