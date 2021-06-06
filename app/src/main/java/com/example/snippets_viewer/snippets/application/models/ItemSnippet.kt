package com.example.snippets_viewer.snippets.application.models

import java.util.*

class ItemSnippet(
        val id: Int,
        val name: String,
        val content: String,
        val createDate: Date?,
        val  updateDate: Date?,
        val createUserId: Long?,
        val updateUserId: Long?,
        val projectId: Int,
        var isChecked: Boolean,
        val language: String
) {
}