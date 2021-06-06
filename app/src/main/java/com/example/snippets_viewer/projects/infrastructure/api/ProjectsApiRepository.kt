package com.example.snippets_viewer.projects.infrastructure.api

import android.content.Context
import android.util.Log
import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.infrastructure.interceptors.AuthorizationRequestInterceptor
import com.example.snippets_viewer.projects.infrastructure.api.models.Project
import com.example.snippets_viewer.users.application.GetUserToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectsApiRepository(val context: Context) {
    private var projetService: ProjectsService? = null

    protected val httpClientBuilder: OkHttpClient.Builder
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY;

            return OkHttpClient()
                    .newBuilder()
                    .addInterceptor(logging)
        }

    init {

        val authRequestInterceptor =
                AuthorizationRequestInterceptor(GetUserToken(context))

        val client = httpClientBuilder
                .addNetworkInterceptor(authRequestInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BaseUrl + ApiConstants.EndPoints.Project)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        projetService = retrofit.create(ProjectsService::class.java)
    }

    fun getAllUserProjects(callback: Callback<List<Project>>) {
        val call = projetService?.getUserProjects()
        call?.enqueue(callback)
    }
}