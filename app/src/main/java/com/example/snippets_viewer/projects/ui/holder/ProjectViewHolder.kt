package com.example.snippets_viewer.projects.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.snippets_viewer.R
import com.example.snippets_viewer.projects.infrastructure.api.models.Project
import com.example.snippets_viewer.projects.ui.adapter.ProjectItemAdapter

class ProjectViewHolder (inflater: LayoutInflater, parent: ViewGroup, projectListener: ProjectItemAdapter.OnProjectListener) :
    RecyclerView.ViewHolder(
        inflater.inflate(R.layout.project_item, parent, false)
    ), View.OnClickListener {

    private var name: TextView? = null
    private var description: TextView? = null
    private var participants: TextView? = null
    private var card: CardView? = null
    private var onProjectListener: ProjectItemAdapter.OnProjectListener? = null
    private var position: Int? = null;



    init {
        name = itemView.findViewById(R.id.project_item_name)
        description = itemView.findViewById(R.id.project_item_desc)
        participants = itemView.findViewById(R.id.project_item_users)
        card = itemView.findViewById(R.id.card_project)
        onProjectListener = projectListener
    }

    fun bind(project: Project, position: Int) {
        name?.text = project.name
        description?.text = "Langage : ${project.language}"
        participants?.text = project.participants.toString()
        this.position = position
        card?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        position?.let {
            onProjectListener?.onProjectClick(it)
        }
    }
}