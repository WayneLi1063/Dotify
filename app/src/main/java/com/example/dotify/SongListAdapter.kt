package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter (private val songList: List<Song>):
    RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val itemSongView = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongListViewHolder(itemSongView)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        val song = songList[position]
        holder.bind(song)
    }

    class SongListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imgSongPreview by lazy { itemView.findViewById<ImageView>(R.id.imgSongPreview) }
        private val txtListSongName by lazy { itemView.findViewById<TextView>(R.id.txtListSongName) }
        private val txtListArtistName by lazy { itemView.findViewById<TextView>(R.id.txtListArtistName) }

        fun bind(song: Song) {
            imgSongPreview.setImageResource(song.smallImageID)
            imgSongPreview.contentDescription = "${song.title} - ${song.artist}"
            txtListSongName.text = song.title
            txtListArtistName.text = song.artist

        }
    }
}