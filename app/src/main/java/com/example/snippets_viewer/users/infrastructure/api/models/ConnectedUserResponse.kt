package com.example.snippets_viewer.users.infrastructure.api.models

data class ConnectedUserResponse(val token: String, val type: String, val id: Long, val username: String, val email: String, val roles: List<String>)
