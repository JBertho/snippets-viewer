package com.example.snippets_viewer.snippets.application.models

import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet

object SnippetAdapter {
    fun adapt(snippet: Snippet, language: String): ItemSnippet {
        return ItemSnippet(
                snippet.id,
                snippet.name,
                snippet.content,
                snippet.createDate,
                snippet.updateDate,
                snippet .createUserId,
                snippet.updateUserId,
                snippet.createUserName,
                snippet.updateUserName,
                snippet.projectId,
                false,
                language
        )
    }

    fun adaptAll(snippets: List<Snippet>, language: String): List<ItemSnippet> {
        var finalList = mutableListOf<ItemSnippet>()
        snippets.forEach { snippet ->
            finalList.add(adapt(snippet, language))
        }

        return finalList
    }
}