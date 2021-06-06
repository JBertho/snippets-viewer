package com.example.snippets_viewer.projects.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snippets_viewer.projects.infrastructure.api.models.Project
import com.example.snippets_viewer.projects.ui.holder.ProjectViewHolder

class ProjectItemAdapter(private val projects: List<Project>, private val onProjectListener: OnProjectListener): RecyclerView.Adapter<ProjectViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProjectViewHolder(inflater, parent, onProjectListener)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project: Project = projects[position]
        holder.bind(project, position)
    }

    override fun getItemCount(): Int = projects.size

    interface OnProjectListener{
        fun onProjectClick(position: Int)
    }


}