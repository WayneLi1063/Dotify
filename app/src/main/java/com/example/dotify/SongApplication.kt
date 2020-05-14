package com.example.dotify

import android.app.Application
import com.example.dotify.manager.ApiManager
import com.example.dotify.manager.MusicManager

class SongApplication : Application() {


    lateinit var musicManager: MusicManager
    lateinit var apiManager: ApiManager

    override fun onCreate() {
        super.onCreate()

        musicManager = MusicManager()
        apiManager = ApiManager(this)


    }

}
