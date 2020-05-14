package com.example.dotify.manager

import android.content.Context
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class MusicManager {

    var currentSong: Song? = null
    var parentSongList: MutableList<Song> = ArrayList(SongDataProvider.getAllSongs())


    fun nextSong(context: Context): Song? {
        if (parentSongList.size == 1) {
            Toast.makeText(context, "There is only one track on the list.", Toast.LENGTH_SHORT)
                .show()
            return currentSong
        }
        var nextSongIndex = parentSongList.indexOf(currentSong) + 1
        if (nextSongIndex >= parentSongList.size) {
            nextSongIndex = 0
        }
        val nextSong = parentSongList[nextSongIndex]
        currentSong = nextSong
        return nextSong
    }

    fun prevSong(context: Context): Song? {
        if (parentSongList.size == 1) {
            Toast.makeText(context, "There is only one track on the list.", Toast.LENGTH_SHORT)
                .show()
            return currentSong
        }
        var prevSongIndex = parentSongList.indexOf(currentSong) - 1
        if (prevSongIndex < 0) {
            prevSongIndex = parentSongList.size - 1
        }
        val prevSong = parentSongList[prevSongIndex]
        currentSong = prevSong
        return prevSong
    }

}