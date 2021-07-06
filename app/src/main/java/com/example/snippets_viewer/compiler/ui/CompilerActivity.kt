package com.example.snippets_viewer.compiler.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snippets_viewer.R
import com.example.snippets_viewer.snippets.infrastructure.api.models.response.CompilationResponse
import com.example.snippets_viewer.snippets.ui.SnippetsActivity
import kotlinx.android.synthetic.main.compiler_activity.*

class CompilerActivity: AppCompatActivity() {


    companion object {

        const val compilerKey = "CompilerKey"
        const val redundancyKey = "RedundancyKey"

        fun start(context: Context, compilerResponse: CompilationResponse) {
            val intent = Intent(context, CompilerActivity::class.java)
            intent.putExtra(compilerKey, compilerResponse.response)
            intent.putExtra(redundancyKey, compilerResponse.redundancy)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.compiler_activity)

        val extras = intent.extras
        extras?. let {
            val responseCompilation = it.getString(compilerKey)
            val responseRedundancy = it.getString(redundancyKey)
            response_compilation?.text = responseCompilation
            response_redundancy?.text = responseRedundancy

        }
    }
}