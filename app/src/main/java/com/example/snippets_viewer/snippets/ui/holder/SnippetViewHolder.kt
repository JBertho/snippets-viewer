package com.example.snippets_viewer.snippets.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snippets_viewer.R
import com.example.snippets_viewer.snippets.application.models.ItemSnippet
import com.example.snippets_viewer.snippets.ui.adapter.SnippetItemAdapter
import io.github.kbiakov.codeview.CodeView

class SnippetViewHolder (inflater: LayoutInflater, parent: ViewGroup, snippetListener: SnippetItemAdapter.OnSnippetListener) :
        RecyclerView.ViewHolder(
                inflater.inflate(R.layout.snippet_item, parent, false)
        ), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private var name: TextView? = null
    private var content: CodeView? = null
    private var icon: ImageView? = null
    private var checkBox: CheckBox? = null


    private var onSnippetListener: SnippetItemAdapter.OnSnippetListener? = null
    private var position: Int? = null
    private lateinit var snippet: ItemSnippet
    private var isVisible = false



    init {
        name = itemView.findViewById(R.id.snippet_item_name)
        content = itemView.findViewById(R.id.snippet_item_desc)
        icon = itemView.findViewById(R.id.snippet_item_icon)
        checkBox = itemView.findViewById(R.id.cb_snippet_item)
        onSnippetListener = snippetListener
    }

    fun bind(snippet: ItemSnippet, position: Int) {
        this.snippet = snippet
        name?.text = snippet.name
        icon?.setImageResource(R.drawable.ic_baseline_chevron_right_48)
        content?.setCode("")
        this.position = position
        itemView.setOnClickListener(this)
        checkBox?.setOnCheckedChangeListener(this)
        checkBox?.isChecked = snippet.isChecked
    }

    override fun onClick(v: View?) {
        position?. let {
            content?. let { codeView ->
                if (!isVisible) {
                    codeView.setCode(snippet.content,snippet.language)
                    codeView.visibility = View.VISIBLE
                    icon?.setImageResource(R.drawable.ic_baseline_expand_more_48)
                } else {
                    codeView.setCode("")
                    codeView.visibility = View.GONE
                    icon?.setImageResource(R.drawable.ic_baseline_chevron_right_48)

                }
                isVisible = !isVisible
            }

            onSnippetListener?.onSnippetClick(it)
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        position?. let {pos ->
            onSnippetListener?.onSnippetCheckBoxChange(pos, isChecked)
        }
    }
}