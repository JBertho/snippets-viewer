package com.example.snippets_viewer.infrastructure.interceptors

import com.example.snippets_viewer.users.application.GetUserToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationRequestInterceptor(val getUserToken: GetUserToken): Interceptor {

    val token
        get() = getUserToken.execute()

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().signedRequest())
    }

    private fun Request.signedRequest(): Request {
        val finalToken = "Bearer $token"
        return this.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
    }
}