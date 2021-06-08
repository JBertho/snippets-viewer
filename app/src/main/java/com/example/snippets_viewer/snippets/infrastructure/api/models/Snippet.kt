package com.example.snippets_viewer.snippets.infrastructure.api.models

import java.util.*

class Snippet(val id: Int, val name: String, val content: String, val createDate: Date, val  updateDate: Date, val createUserId: Long, val updateUserId: Long,val updateUserName: String, val createUserName: String, val projectId: Int){
}