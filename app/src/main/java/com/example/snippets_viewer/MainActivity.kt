package com.example.snippets_viewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.snippets_viewer.users.infrastructure.api.UsersApiRepository
import com.example.snippets_viewer.users.infrastructure.api.models.ConnectedUserResponse
import com.example.snippets_viewer.users.infrastructure.api.models.LoginRequest
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        Log.d("TEST", "LETS GOOO")
                        response.body()?.toString()?.let { Log.d("TEST", it) }
                    }

                    override fun onFailure(call: Call<ConnectedUserResponse>, t: Throwable) {
                        Log.d("TEST", "TA RACE")
                        Log.d("TEST",t.message.toString())
                    }

                })
            }

        }
    }


}