package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"
        val songList = SongDataProvider.getAllSongs()
        val songListAdapter = SongListAdapter(songList as MutableList<Song>)
        rvSongList.adapter = songListAdapter

        songListAdapter.onSongClickListener = {title, artist ->
            txtMiniPlayer.text = getString(R.string.miniPlayer).format(title, artist)
        }

        songListAdapter.onSongLongClickListener = {title, position ->
            Toast.makeText(this, getString(R.string.songRemoval).format(title), Toast.LENGTH_SHORT).show()
            songList.removeAt(position)
        }

        btnShuffle.setOnClickListener {
            val newSongList = songList.toMutableList()
            newSongList.shuffle()
            songListAdapter.shuffle(newSongList)
        }

    }
}
