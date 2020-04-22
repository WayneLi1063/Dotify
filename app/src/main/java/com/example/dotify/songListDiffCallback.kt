package com.example.dotify

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class SongListDiffCallback(private val oldSongList: MutableList<Song>, private val newSongList: MutableList<Song>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldSongList[oldItemPosition]
        val newSong = newSongList[newItemPosition]
        return oldSong.id == newSong.id
    }

    override fun getOldListSize(): Int {
        return oldSongList.size
    }

    override fun getNewListSize(): Int {
        return newSongList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldSongList[oldItemPosition]
        val newSong = newSongList[newItemPosition]
        return oldSong.title == newSong.title && oldSong.artist == newSong.artist
    }

}