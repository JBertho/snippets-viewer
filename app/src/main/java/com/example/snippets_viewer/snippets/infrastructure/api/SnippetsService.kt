package com.example.snippets_viewer.snippets.infrastructure.api

import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import com.example.snippets_viewer.snippets.infrastructure.api.models.request.CompileRequest
import com.example.snippets_viewer.snippets.infrastructure.api.models.response.CompilationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface SnippetsService {
    @GET("${ApiConstants.BaseUrl}${ApiConstants.EndPoints.Snippets}project/{projectId}")
    fun getUserProjects(@Path("projectId") projectId: Int): Call<List<Snippet>>

    @POST("${ApiConstants.BaseUrl}compiler")
    fun compile(@Body compileRequest: CompileRequest): Call<CompilationResponse>
}