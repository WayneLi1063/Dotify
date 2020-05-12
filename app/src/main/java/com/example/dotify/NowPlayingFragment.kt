package com.example.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {

    private var randomNumber = -1

    private var application: SongApplication? = null

    private var skipListener: SkipListener? = null

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val STATE_PLAYS = "number_of_plays"

        fun getInstance(): NowPlayingFragment {
            return NowPlayingFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        application = context.applicationContext as SongApplication

        if (context is SkipListener) {
            skipListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                randomNumber = getInt(STATE_PLAYS, Random.nextInt(1000, 1000000000))
            }
        } else {
            randomNumber = Random.nextInt(1000, 1000000000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return layoutInflater.inflate(R.layout.fragment_now_playing, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)

        var song = application?.currentSong

        if (song != null) {
            txtSongTitle.text = song.title
            txtArtist.text = song.artist
            imgDivide.setImageResource(song.largeImageID)
        }

        imgPrev.setOnClickListener {
            if (song != null) {
                val immutableSong = song
                if (immutableSong != null) {
                    skipListener?.onSkipPreviousListener(immutableSong)
                }
                song = application?.currentSong
            }
        }

        imgPlay.setOnClickListener {
            randomNumber += 1
            txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)
        }

        imgNext.setOnClickListener {
            if (song != null) {
                val immutableSong = song
                if (immutableSong != null) {
                    skipListener?.onSkipNextListener(immutableSong)
                }
                song = application?.currentSong
            }
        }

    }

    fun updateSong(song: Song) {
        txtSongTitle.text = song.title
        txtArtist.text = song.artist
        imgDivide.setImageResource(song.largeImageID)
        randomNumber = Random.nextInt(1000, 1000000000)
        txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(STATE_PLAYS, randomNumber)
        }
    }
}

interface SkipListener {
    fun onSkipPreviousListener(song: Song)

    fun onSkipNextListener(song: Song)
}