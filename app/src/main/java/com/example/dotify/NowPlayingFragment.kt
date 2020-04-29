package com.example.dotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {

    private var randomNumber = -1

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG_KEY = "song"
        const val STATE_PLAYS = "number_of_plays"
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

        arguments?.let { args ->
            val song = args.getParcelable<Song>(SONG_KEY)
            if (song != null) {
                txtSongTitle.text = song.title
                txtArtist.text = song.artist
                imgDivide.setImageResource(song.largeImageID)
            }

        }

        imgPrev.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        imgPlay.setOnClickListener {
            randomNumber += 1
            txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)
        }

        imgNext.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

    }

    fun updateSong(song: Song) {
        txtSongTitle.text = song.title
        txtArtist.text = song.artist
        imgDivide.setImageResource(song.largeImageID)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(STATE_PLAYS, randomNumber)
        }
    }
}