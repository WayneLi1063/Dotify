package com.example.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {

    private lateinit var parentSongList: MutableList<Song>
    private lateinit var songListAdapter: SongListAdapter

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val SONG_LIST_KEY = "song_list"
        const val STATE_SONG_LIST = "parent_song_list"
    }

    private var onSongClickedListener: OnSongClickedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickedListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                parentSongList = getParcelableArrayList<Song>(STATE_SONG_LIST) as MutableList<Song>
            }
        } else {
            arguments?.let { args ->
                parentSongList =
                    args.getParcelableArrayList<Song>(SONG_LIST_KEY) as MutableList<Song>
            }
        }

        return layoutInflater.inflate(R.layout.fragment_song_list, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songListAdapter = SongListAdapter(parentSongList)
        rvSongList.adapter = songListAdapter

        songListAdapter.onSongClickListener = { song ->
            onSongClickedListener?.onSongClicked(song)
        }

    }

    fun shuffleList() {
        parentSongList.shuffle()
        val newSongList: MutableList<Song> = parentSongList
        songListAdapter.shuffle(newSongList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(STATE_SONG_LIST, parentSongList as ArrayList<Song>)
    }

}

interface OnSongClickedListener {
    fun onSongClicked(song: Song)
}