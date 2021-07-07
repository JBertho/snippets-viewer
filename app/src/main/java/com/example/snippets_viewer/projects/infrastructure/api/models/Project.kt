package com.example.snippets_viewer.projects.infrastructure.api.models

import java.util.*

class Project(val id: Int, val name: String, val language: String, val createDate: Date, val createUserId: Long, val lastCompilation: Date, val token: String, val participants: Int) {

}