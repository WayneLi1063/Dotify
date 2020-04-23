package com.example.dotify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.MainActivity.Companion.ALBUM_IMG_KEY
import com.example.dotify.MainActivity.Companion.ARTIST_KEY
import com.example.dotify.MainActivity.Companion.TITLE_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    var currentSongPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"
        var parentSongList = SongDataProvider.getAllSongs() as MutableList<Song>
        val songListAdapter = SongListAdapter(parentSongList)
        rvSongList.adapter = songListAdapter

        songListAdapter.onSongClickListener = { title, artist, position ->
            txtMiniPlayer.text = getString(R.string.miniPlayer).format(title, artist)
            currentSongPosition = position
        }

        songListAdapter.onSongLongClickListener = {title, position ->
            Toast.makeText(this, getString(R.string.songRemoval).format(title), Toast.LENGTH_SHORT).show()
            songListAdapter.songList.removeAt(position)
        }

        btnShuffle.setOnClickListener {
            val newSongList = parentSongList.toMutableList()
            newSongList.shuffle()
            songListAdapter.shuffle(newSongList)
            rvSongList.scrollToPosition(0)
        }

        txtMiniPlayer.setOnClickListener {
            onMiniPlayClicked(songListAdapter)
        }
    }

    fun onMiniPlayClicked(songListAdapter: SongListAdapter) {
        val currentSongList = songListAdapter.songList
        if (currentSongPosition != -1) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(TITLE_KEY, currentSongList[currentSongPosition].title)
            intent.putExtra(ARTIST_KEY, currentSongList[currentSongPosition].artist)
            intent.putExtra(ALBUM_IMG_KEY, currentSongList[currentSongPosition].largeImageID)

            startActivity(intent)
        }
    }
}
