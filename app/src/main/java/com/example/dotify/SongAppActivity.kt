package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_app.*
import java.util.ArrayList

// TODO: Fix shuffle, fix NowPlayingFragment disappear after rotate, create landscape layout and values, clean up dead codes


class SongAppActivity : AppCompatActivity(), OnSongClickedListener {

    private var currentSong: Song? = null

    companion object {
        const val STATE_CUR_SONG = "current_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_app)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                currentSong = getParcelable(STATE_CUR_SONG)
                txtMiniPlayer.text = getString(R.string.miniPlayer).format(currentSong?.title, currentSong?.artist)
            }
        }

        val songListFragment = SongListFragment()

        val parentSongList = SongDataProvider.getAllSongs()
        val songListBundle = Bundle().apply {
            putParcelableArrayList(SongListFragment.SONG_LIST_KEY, parentSongList as ArrayList<Song>)
        }

        songListFragment.arguments = songListBundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

//        songListFragment.onSongClickedListener = { title, artist, position ->
//            txtMiniPlayer.text = getString(R.string.miniPlayer).format(title, artist)
//            currentSongPosition = position
//        }

        btnShuffle.setOnClickListener {
//            val newSongList = parentSongList.toMutableList()
//            newSongList.shuffle()
//            songListAdapter.shuffle(newSongList)
//            rvSongList.scrollToPosition(0)
            getSongListFragment()?.shuffleList()
        }

        txtMiniPlayer.setOnClickListener {
            onMiniPlayClicked()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        miniPlayer.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        miniPlayer.visibility = View.VISIBLE
    }

    override fun onSongClicked(song: Song) {
            txtMiniPlayer.text = getString(R.string.miniPlayer).format(song.title, song.artist)
            currentSong = song
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayClicked() {
        if (currentSong != null) {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra(TITLE_KEY, currentSongList[currentSongPosition].title)
//            intent.putExtra(ARTIST_KEY, currentSongList[currentSongPosition].artist)
//            intent.putExtra(ALBUM_IMG_KEY, currentSongList[currentSongPosition].largeImageID)
//
//            startActivity(intent)
            var nowPlayingFragment = getNowPlayingFragment()

            if (nowPlayingFragment == null) {
                val nowPlayingFragment = NowPlayingFragment()
                val nowPlayingBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.SONG_KEY, currentSong)
                }
                nowPlayingFragment.arguments = nowPlayingBundle

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, nowPlayingFragment)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragment.updateSongInfo(currentSong!!)
            }
            miniPlayer.visibility = View.INVISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(STATE_CUR_SONG, currentSong)
    }
}
