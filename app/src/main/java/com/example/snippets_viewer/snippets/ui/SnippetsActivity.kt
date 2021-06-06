package com.example.snippets_viewer.snippets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snippets_viewer.R
import com.example.snippets_viewer.snippets.application.models.SnippetAdapter
import com.example.snippets_viewer.snippets.application.models.ItemSnippet
import com.example.snippets_viewer.snippets.infrastructure.api.SnippetsApiRepository
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import com.example.snippets_viewer.snippets.ui.adapter.SnippetItemAdapter
import kotlinx.android.synthetic.main.project_list_activity.*
import kotlinx.android.synthetic.main.snippets_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SnippetsActivity: AppCompatActivity(), SnippetItemAdapter.OnSnippetListener {

    private lateinit var snippetsApiRepository: SnippetsApiRepository
    private var listSnippet: List<ItemSnippet> = emptyList()

    companion object {

        const val projectIdKey = "ProjectId"

        fun start(context: Context, projectId: Int) {
            val intent = Intent(context, SnippetsActivity::class.java)
            intent.putExtra(projectIdKey, projectId)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snippets_activity)
        snippetsApiRepository = SnippetsApiRepository(this)
        val extras = intent.extras
        extras?. let {
            val projectId = it.getInt(projectIdKey)
            this.loadSnippets(projectId)
        }


    }

    private fun loadSnippets(projectId: Int) {
        snippetsApiRepository.getSnippetsOfProject(projectId, object : Callback<List<Snippet>> {
            override fun onResponse(call: Call<List<Snippet>>, response: Response<List<Snippet>>) {
                response.body()?.let {snippets ->
                    listSnippet = SnippetAdapter.adaptAll(snippets,"c");
                    snippets_rv?.apply {

                        layoutManager = LinearLayoutManager(this@SnippetsActivity)
                        adapter = SnippetItemAdapter(listSnippet, this@SnippetsActivity,this@SnippetsActivity)
                    }
                }
            }

            override fun onFailure(call: Call<List<Snippet>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onSnippetClick(position: Int) {
        Toast.makeText(this, "Il a touché le numéro $position", Toast.LENGTH_SHORT).show()
    }

    override fun onSnippetCheckBoxChange(pos: Int, checked: Boolean) {
        listSnippet.get(pos).isChecked = checked
        listSnippet.forEach { snippetItem ->
            Log.d("TEST", "valeur du booléan pour ${snippetItem.name} == ${snippetItem.isChecked}")
        }
    }
}