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

    private lateinit var fragmentSongList: MutableList<Song>
    private lateinit var songListAdapter: SongListAdapter

    private var onSongClickedListener: OnSongClickedListener? = null

    private var application: SongApplication? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName

        fun getInstance(): SongListFragment {
            return SongListFragment()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        application = context.applicationContext as SongApplication

        if (context is OnSongClickedListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                parentSongList = getParcelableArrayList<Song>(STATE_SONG_LIST) as MutableList<Song>
//            }
//        } else {
//            arguments?.let { args ->
//                parentSongList =
//                    args.getParcelableArrayList<Song>(SONG_LIST_KEY) as MutableList<Song>
//            }
//        }
        fragmentSongList = application?.parentSongList ?: listOf<Song>() as MutableList<Song>

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
        application?.let {
            it.parentSongList.shuffle()
        }
        val newSongList = application?.parentSongList
        if (newSongList != null) {
            songListAdapter.shuffle(newSongList)
        }
    }
}

interface OnSongClickedListener {
    fun onSongClicked(song: Song)
}