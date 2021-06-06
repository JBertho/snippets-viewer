package com.example.snippets_viewer.snippets.infrastructure.api

import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface SnippetsService {
    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Snippets}project/{projectId}")
    fun getUserProjects(@Path("projectId") projectId: Int): Call<List<Snippet>>
}