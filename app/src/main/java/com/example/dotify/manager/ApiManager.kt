package com.example.dotify.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.dotify.model.UserInfo
import com.google.gson.Gson

class ApiManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getUserInfo(onUserInfoReady: (UserInfo) -> Unit, onError: (() -> Unit)? = null) {
        val userInfoURL =
            "https://raw.githubusercontent.com/echeeUW/codesnippets/master/user_info.json"

        val request = StringRequest(
            Request.Method.GET, userInfoURL,
            { response ->
                // success

                val gson = Gson()
                val userInfo = gson.fromJson(response, UserInfo::class.java)

                onUserInfoReady(userInfo)

            }, {
                // error
                onError?.invoke()
            }
        )
        queue.add(request)
    }
}