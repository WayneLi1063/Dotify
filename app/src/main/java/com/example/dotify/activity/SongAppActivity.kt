package com.example.dotify.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.example.dotify.R
import com.example.dotify.SongApplication
import com.example.dotify.fragment.NowPlayingFragment
import com.example.dotify.fragment.OnSongClickedListener
import com.example.dotify.fragment.SkipListener
import com.example.dotify.fragment.SongListFragment
import com.example.dotify.manager.MusicManager
import kotlinx.android.synthetic.main.activity_song_app.*


class SongAppActivity : AppCompatActivity(),
    OnSongClickedListener,
    SkipListener {

    private lateinit var musicManager: MusicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_app)

        musicManager = (applicationContext as SongApplication).musicManager

        if (getSongListFragment() == null) {
            val songListFragment =
                SongListFragment.getInstance()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragContainer, songListFragment,
                    SongListFragment.TAG
                )
                .commit()

        } else {
            val currentSong = musicManager.currentSong
            if (currentSong != null) {
                txtMiniPlayer.text =
                    getString(R.string.miniPlayer).format(currentSong.title, currentSong.artist)
            }
        }

        if (getNowPlayingFragment() != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            miniPlayer.visibility = View.GONE
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasNowPlaying = supportFragmentManager.backStackEntryCount > 0
            if (!hasNowPlaying) {
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
        musicManager.currentSong = song
    }

    private fun getSongListFragment() =
        supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    private fun getNowPlayingFragment() =
        supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayClicked() {
        val immutableCurSong = musicManager.currentSong
        if (immutableCurSong != null) {
            val nowPlayingFragmentRef = getNowPlayingFragment()

            if (nowPlayingFragmentRef == null) {
                val nowPlayingFragment =
                    NowPlayingFragment.getInstance()

                miniPlayer.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.fragContainer, nowPlayingFragment,
                        NowPlayingFragment.TAG
                    )
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            } else {
                nowPlayingFragmentRef.updateSong(immutableCurSong)
            }
        }
    }

    override fun onSkipPreviousListener(song: Song) {
        val prevSong = musicManager.prevSong(this)
        if (prevSong != null) {
            getNowPlayingFragment()?.updateSong(prevSong)
        }
    }

    override fun onSkipNextListener(song: Song) {
        val nextSong = musicManager.nextSong(this)
        if (nextSong != null) {
            getNowPlayingFragment()?.updateSong(nextSong)
        }
    }

}
