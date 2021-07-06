package com.example.snippets_viewer.snippets.infrastructure.api

import android.content.Context
import com.example.snippets_viewer.infrastructure.ApiConstants
import com.example.snippets_viewer.infrastructure.interceptors.AuthorizationRequestInterceptor
import com.example.snippets_viewer.projects.infrastructure.api.ProjectsService
import com.example.snippets_viewer.snippets.application.models.ItemSnippet
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import com.example.snippets_viewer.snippets.infrastructure.api.models.request.CompileRequest
import com.example.snippets_viewer.snippets.infrastructure.api.models.response.CompilationResponse
import com.example.snippets_viewer.users.application.GetUserToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SnippetsApiRepository(val context: Context) {
    private var snippetsService: SnippetsService? = null

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
                .baseUrl(ApiConstants.BaseUrl + ApiConstants.EndPoints.Snippets)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        snippetsService = retrofit.create(SnippetsService::class.java)
    }

    fun getSnippetsOfProject(projectId: Int, callback: Callback<List<Snippet>>) {
        val call = snippetsService?.getUserProjects(projectId)
        call?.enqueue(callback)
    }

    fun compileSnippets(checkedSnippets: List<ItemSnippet>, projectId: Int, callback: Callback<CompilationResponse>) {
        val compileRequest = CompileRequest(checkedSnippets.map { snippet -> snippet.id }, projectId)
        val call = snippetsService?.compile(compileRequest)

        call?.enqueue(callback)
    }
}