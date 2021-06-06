package com.example.snippets_viewer.projects.infrastructure.api

import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.projects.infrastructure.api.models.Project
import retrofit2.Call
import retrofit2.http.GET

interface ProjectsService {

    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Project}me")
    fun getUserProjects(): Call<List<Project>>
}