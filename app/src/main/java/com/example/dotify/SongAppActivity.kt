package com.example.dotify

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_app.*
import java.util.*


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
        } else {
            val songListFragment = SongListFragment()

            val parentSongList = ArrayList<Song>(SongDataProvider.getAllSongs())
            val arraySongList = ArrayList<Song>(parentSongList)
            val songListBundle = Bundle().apply {
                putParcelableArrayList(
                    SongListFragment.SONG_LIST_KEY,
                    arraySongList
                )
            }
            songListFragment.arguments = songListBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment)
                .commit()
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            miniPlayer.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if (!hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayer.visibility = View.VISIBLE
            }
        }

        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }

        miniPlayer.setOnClickListener {
            onMiniPlayClicked()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onSongClicked(song: Song) {
            txtMiniPlayer.text = getString(R.string.miniPlayer).format(song.title, song.artist)
            currentSong = song
    }

    private fun getSongListFragment() =
        supportFragmentManager.findFragmentById(R.id.fragContainer) as? SongListFragment

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayClicked() {
        val immutableCurSong = currentSong
        if (immutableCurSong != null) {
            val nowPlayingFragmentRef = getNowPlayingFragment()

            if (nowPlayingFragmentRef == null) {
                val nowPlayingFragment = NowPlayingFragment()
                val nowPlayingBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.SONG_KEY, immutableCurSong)
                }
                nowPlayingFragment.arguments = nowPlayingBundle

                miniPlayer.visibility = View.INVISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragmentRef.updateSong(immutableCurSong)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE_CUR_SONG, currentSong)
    }
}
