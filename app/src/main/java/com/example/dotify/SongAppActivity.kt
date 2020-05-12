package com.example.dotify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_song_app.*


class SongAppActivity : AppCompatActivity(), OnSongClickedListener, SkipListener {

    private var application: SongApplication? = null

//    private var currentSong: Song? = null
//
//    companion object {
//        const val STATE_CUR_SONG = "current_song"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_app)

        application = applicationContext as SongApplication

//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                currentSong = getParcelable(STATE_CUR_SONG)
//                txtMiniPlayer.text = getString(R.string.miniPlayer).format(currentSong?.title, currentSong?.artist)
//            }
//        } else {
//
//            val parentSongList = ArrayList<Song>(SongDataProvider.getAllSongs())
//
//        }
        if (getSongListFragment() == null) {
            val songListFragment = SongListFragment.getInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()

        } else {
            val currentSong = application!!.currentSong
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
        application!!.currentSong = song
    }

    private fun getSongListFragment() =
        supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    private fun getNowPlayingFragment() =
        supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayClicked() {
        val immutableCurSong = application!!.currentSong
        if (immutableCurSong != null) {
            val nowPlayingFragmentRef = getNowPlayingFragment()

            if (nowPlayingFragmentRef == null) {
                val nowPlayingFragment = NowPlayingFragment.getInstance()

                miniPlayer.visibility = View.GONE
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

    override fun onSkipPreviousListener(song: Song) {
        val parentSongList = application!!.parentSongList
        if (parentSongList.size == 1) {
            Toast.makeText(this, "There is only one track on the list.", Toast.LENGTH_SHORT).show()
            return
        }
        var prevSongIndex = parentSongList.indexOf(song) - 1
        if (prevSongIndex < 0) {
            prevSongIndex = parentSongList.size - 1
        }
        val prevSong = parentSongList[prevSongIndex]
        application!!.currentSong = prevSong
        getNowPlayingFragment()?.updateSong(prevSong)
    }

    override fun onSkipNextListener(song: Song) {
        val parentSongList = application!!.parentSongList
        if (parentSongList.size == 1) {
            Toast.makeText(this, "There is only one track on the list.", Toast.LENGTH_SHORT).show()
            return
        }
        var nextSongIndex = parentSongList.indexOf(song) + 1
        if (nextSongIndex >= parentSongList.size) {
            nextSongIndex = 0
        }
        val nextSong = parentSongList[nextSongIndex]
        application!!.currentSong = nextSong
        getNowPlayingFragment()?.updateSong(nextSong)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelable(STATE_CUR_SONG, currentSong)
//    }
}
