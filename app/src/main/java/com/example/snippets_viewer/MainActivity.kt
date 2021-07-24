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
import kotlinx.android.synthetic.main.activity_main.error_screen
import kotlinx.android.synthetic.main.activity_main.tv_display_error
import kotlinx.android.synthetic.main.snippets_activity.*
import org.json.JSONObject
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
            close_error_login -> closeError()

        }
    }

    private fun tryLogin() {
        tv_bad_credentials.visibility = View.INVISIBLE;

        ti_login.text?.let { login ->
            ti_password.text?.let { password ->
                UsersApiRepository.signIn(LoginRequest(login.toString(), password.toString()), object: Callback<ConnectedUserResponse> {
                    override fun onResponse(
                        call: Call<ConnectedUserResponse>,
                        response: Response<ConnectedUserResponse>
                    ) {
                        if (!response.isSuccessful) {
                            handleError(response)
                        } else {
                            response.body()?.let { connectedUser ->
                                updateUserStorage.execute(connectedUser)
                                navigateToProjectList()
                            }
                        }

                    }

                    override fun onFailure(call: Call<ConnectedUserResponse>, t: Throwable) {
                    }

                })
            }

        }
    }

    private fun<T: Any> handleError(response: Response<T>) {
        response.errorBody()?.let { error ->
            val jObjError = JSONObject(error.string())
            var errorCode = jObjError.getString("status")
            if (errorCode.equals("401")) {
                showLoginError()
            } else {
                var errorMessage = jObjError.getString("status")
                if (errorMessage.isEmpty()) {
                    errorMessage = "Unknown error"
                }
                Log.d("TEST", jObjError.toString());
                showError(errorMessage)
            }
        } ?: run {
            showError("Unknown error")
        }
    }

    private fun showError(errorMessage: String) {
        tv_display_error.text = errorMessage
        error_screen.visibility = View.VISIBLE
    }

    private fun showLoginError() {
        tv_bad_credentials.visibility = View.VISIBLE;
    }

    private fun closeError() {
        error_screen.visibility = View.INVISIBLE
    }

    private fun navigateToProjectList() {
        ProjectListActivity.start(this);
    }


}