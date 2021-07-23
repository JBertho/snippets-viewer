package com.example.snippets_viewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.snippets_viewer.projects.infrastructure.api.ProjectsApiRepository
import com.example.snippets_viewer.projects.infrastructure.api.models.Project
import com.example.snippets_viewer.projects.ui.ProjectListActivity
import com.example.snippets_viewer.snippets.infrastructure.api.SnippetsApiRepository
import com.example.snippets_viewer.snippets.infrastructure.api.models.Snippet
import com.example.snippets_viewer.users.application.UpdateUserStorage
import com.example.snippets_viewer.users.application.UpdateUserToken
import com.example.snippets_viewer.users.infrastructure.api.UsersApiRepository
import com.example.snippets_viewer.users.infrastructure.api.models.ConnectedUserResponse
import com.example.snippets_viewer.users.infrastructure.api.models.LoginRequest
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var snippetsApiRepository: SnippetsApiRepository
    private lateinit var updateUserStorage: UpdateUserStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        snippetsApiRepository = SnippetsApiRepository(this.applicationContext)
        updateUserStorage = UpdateUserStorage(this.applicationContext)
        setContentView(R.layout.activity_main)
        btn_login.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v) {
            btn_login -> tryLogin()
        }
    }

    private fun tryLogin() {
        ti_login.text?.let { login ->
            ti_password.text?.let { password ->
                UsersApiRepository.signIn(LoginRequest(login.toString(), password.toString()), object: Callback<ConnectedUserResponse> {
                    override fun onResponse(
                        call: Call<ConnectedUserResponse>,
                        response: Response<ConnectedUserResponse>
                    ) {
                        response.body()?.let { connectedUser ->
                            updateUserStorage.execute(connectedUser)
                            navigateToProjectList()
                        }
                    }

                    override fun onFailure(call: Call<ConnectedUserResponse>, t: Throwable) {
                    }

                })
            }

        }
    }

    private fun navigateToProjectList() {
        ProjectListActivity.start(this);
    }


}