package com.example.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import java.util.*

class SongApplication : Application() {

    lateinit var parentSongList: MutableList<Song>
    var currentSong: Song? = null

    override fun onCreate() {
        super.onCreate()

        parentSongList = ArrayList(SongDataProvider.getAllSongs())
    }

}