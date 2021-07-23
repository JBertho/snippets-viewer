package com.example.snippets_viewer.snippets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snippets_viewer.R
import com.example.snippets_viewer.compiler.ui.CompilerActivity
import com.example.snippets_viewer.snippets.application.models.ItemSnippet
import com.example.snippets_viewer.snippets.application.models.SnippetAdapter
import com.example.snippets_viewer.snippets.infrastructure.api.SnippetsApiRepository
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import com.example.snippets_viewer.snippets.infrastructure.api.models.response.CompilationResponse
import com.example.snippets_viewer.snippets.ui.adapter.SnippetItemAdapter
import kotlinx.android.synthetic.main.project_list_activity.*
import kotlinx.android.synthetic.main.snippets_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SnippetsActivity: AppCompatActivity(), SnippetItemAdapter.OnSnippetListener,
    View.OnClickListener {

    private lateinit var snippetsApiRepository: SnippetsApiRepository
    private var listSnippet: List<ItemSnippet> = emptyList()
    var projectId: Int? = null

    private val ADD_ALL = "ADD"
    private val REMOVE_ALL = "REMOVE"

    var allActionState = ADD_ALL;

    companion object {

        const val projectIdKey = "ProjectId"
        const val projectNameKey = "ProjectName"

        fun start(context: Context, projectId: Int, projectName: String) {
            val intent = Intent(context, SnippetsActivity::class.java)
            intent.putExtra(projectIdKey, projectId)
            intent.putExtra(projectNameKey, projectName)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snippets_activity)
        snippetsApiRepository = SnippetsApiRepository(this)

        val extras = intent.extras
        extras?. let {
            projectId = it.getInt(projectIdKey)
            if (projectId != null) {
                this.loadSnippets(projectId!!)
            }
            snippet_title?.text = it.getString(projectNameKey)
        }

        compileButton?.setOnClickListener(this)
        btn_all_action?.setOnClickListener(this)
        close_error.setOnClickListener(this)


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
    }

    override fun onSnippetCheckBoxChange(pos: Int, checked: Boolean) {
        listSnippet.get(pos).isChecked = checked
        verifyCheckedSnippets()

    }

    private fun verifyCheckedSnippets() {
        val checkedSnippets = listSnippet.filter { snippet -> snippet.isChecked }
        if (checkedSnippets.size > 0) {
            allActionState = REMOVE_ALL
        } else {
            allActionState = ADD_ALL
        }
        updateAllActionButton()
    }

    private fun updateAllActionButton() {
        if (allActionState == REMOVE_ALL) {
            btn_all_action.text = getString(R.string.uncheck_all)
            btn_all_action.backgroundTintList = ContextCompat.getColorStateList(this ,R.color.uncheckAllColor);
        }
        if (allActionState == ADD_ALL) {
            btn_all_action.text = getString(R.string.check_all)
            btn_all_action.backgroundTintList = ContextCompat.getColorStateList(this ,R.color.checkAllColor);
        }
    }

    override fun onClick(v: View?) {
        if (v == compileButton) {
            compileCode()
        } else if (v == btn_all_action) {
            startAllActionButton()
        } else if (v == close_error) {
            closeError()
        }
    }

    private fun closeError() {
        error_screen.visibility = View.INVISIBLE
    }

    private fun startAllActionButton() {
        if (allActionState == ADD_ALL) {
            listSnippet.forEach { snippet -> snippet.isChecked = true }
            allActionState = REMOVE_ALL
        } else {
            listSnippet.forEach { snippet -> snippet.isChecked = false }
            allActionState = ADD_ALL
        }
        snippets_rv?.apply {

            layoutManager = LinearLayoutManager(this@SnippetsActivity)
            adapter = SnippetItemAdapter(listSnippet, this@SnippetsActivity,this@SnippetsActivity)
        }
        updateAllActionButton()
    }

    private fun compileCode() {
        loading_creen.visibility = View.VISIBLE
        val checkedSnippets = listSnippet.filter { snippet -> snippet.isChecked }
        if (projectId !== null) {
            snippetsApiRepository.compileSnippets(checkedSnippets, projectId!!, object : Callback<CompilationResponse> {
                override fun onResponse(
                    call: Call<CompilationResponse>,
                    response: Response<CompilationResponse>
                ) {
                    loading_creen.visibility = View.GONE
                    if (!response.isSuccessful) {

                        handleError(response)

                    } else {
                        response.body()?.let { compilationResponse ->
                            CompilerActivity.start(this@SnippetsActivity,compilationResponse)
                        }
                    }
                }

                override fun onFailure(call: Call<CompilationResponse>, t: Throwable) {
                    loading_creen.visibility = View.GONE
                    tv_display_error.text = t.message
                    error_screen.visibility = View.VISIBLE
                }


            })
        } else {
            showError("Missing Project Informations")
        }
    }

    private fun<T: Any> handleError(response: Response<T>) {
        response.errorBody()?.let { error ->
            val jObjError = JSONObject(error.string())
            var errorMessage = jObjError.getString("message")
            if (errorMessage.isEmpty()) {
                errorMessage = "Unknown error"
            }
            showError(errorMessage)
        } ?: run {
            showError("Unknown error")
        }
    }

    private fun showError(errorMessage: String) {
        tv_display_error.text = errorMessage
        error_screen.visibility = View.VISIBLE
    }
}