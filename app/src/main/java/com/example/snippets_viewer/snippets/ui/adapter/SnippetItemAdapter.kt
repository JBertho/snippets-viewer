package com.example.snippets_viewer.snippets.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snippets_viewer.snippets.application.models.ItemSnippet
import com.example.snippets_viewer.snippets.ui.holder.SnippetViewHolder

class SnippetItemAdapter (private val snippets: List<ItemSnippet>, private val onProjectListener: OnSnippetListener, private val context: Context): RecyclerView.Adapter<SnippetViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnippetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SnippetViewHolder(inflater, parent, onProjectListener)
    }

    override fun onBindViewHolder(holder: SnippetViewHolder, position: Int) {
        val snippet: ItemSnippet = snippets[position]
        holder.bind(snippet, position)
    }

    override fun getItemCount(): Int = snippets.size

    interface OnSnippetListener{
        fun onSnippetClick(position: Int)
        fun onSnippetCheckBoxChange(pos: Int, checked: Boolean)
    }
}
