package com.example.snippets_viewer.projects.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snippets_viewer.R
import com.example.snippets_viewer.projects.infrastructure.api.ProjectsApiRepository
import com.example.snippets_viewer.projects.infrastructure.api.models.Project
import com.example.snippets_viewer.projects.ui.adapter.ProjectItemAdapter
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import com.example.snippets_viewer.snippets.ui.SnippetsActivity
import kotlinx.android.synthetic.main.project_list_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectListActivity: AppCompatActivity(), ProjectItemAdapter.OnProjectListener {

    private lateinit var projectsApiRepository: ProjectsApiRepository

    private var projectList: List<Project> = emptyList()

    companion object {
        fun start(context: Context): Context {
            val intent = Intent(context, ProjectListActivity::class.java)
            context.startActivity(intent)
            return context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_list_activity)
        projectsApiRepository = ProjectsApiRepository(this.applicationContext)

        projectsApiRepository.getAllUserProjects(object : Callback<List<Project>> {
            override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
                response.body()?.let {projects ->
                    projectList = projects;
                    project_rv?.apply {

                        layoutManager = LinearLayoutManager(this@ProjectListActivity)
                        adapter = ProjectItemAdapter(projects, this@ProjectListActivity)
                    }
                }
            }

            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
            }

        })

    }

    override fun onProjectClick(position: Int) {
        val project = projectList.get(position)
        SnippetsActivity.start(this, project.id)
    }
}