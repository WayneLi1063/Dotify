package com.example.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.dotify.R
import com.example.dotify.SongApplication
import com.example.dotify.SongListAdapter
import com.example.dotify.manager.MusicManager
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {

    private lateinit var fragmentSongList: MutableList<Song>
    private lateinit var songListAdapter: SongListAdapter

    private var onSongClickedListener: OnSongClickedListener? = null

    private lateinit var musicManager: MusicManager

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName

        fun getInstance(): SongListFragment {
            return SongListFragment()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        musicManager = (context.applicationContext as SongApplication).musicManager

        if (context is OnSongClickedListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentSongList = musicManager.parentSongList

        return layoutInflater.inflate(R.layout.fragment_song_list, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songListAdapter = SongListAdapter(fragmentSongList)
        rvSongList.adapter = songListAdapter

        songListAdapter.onSongClickListener = { song ->
            onSongClickedListener?.onSongClicked(song)
        }

    }

    fun shuffleList() {
        musicManager.parentSongList.shuffle()
        val newSongList = musicManager.parentSongList
        songListAdapter.shuffle(newSongList)
    }
}

interface OnSongClickedListener {
    fun onSongClicked(song: Song)
}