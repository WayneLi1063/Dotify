package com.example.dotify.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dotify.R
import com.example.dotify.SongApplication
import com.example.dotify.manager.ApiManager
import com.example.dotify.manager.MusicManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlin.random.Random

class UserInfoActivity : AppCompatActivity() {

    private lateinit var apiManager: ApiManager
    private lateinit var musicManager: MusicManager
    private var randomNumber = -1

    companion object {
        const val STATE_FOLLOWER = "number_of_followers"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        title = "USER INFO"

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                randomNumber = getInt(STATE_FOLLOWER, Random.nextInt(2, 10000))
            }
        } else {
            randomNumber = Random.nextInt(2, 10000)
        }

        musicManager = (applicationContext as SongApplication).musicManager
        apiManager = (applicationContext as SongApplication).apiManager

        apiManager.getUserInfo({ userInfo ->
            txtUserName.text = userInfo.username
            Picasso.get().load(userInfo.profilePicURL).error(R.drawable.ic_person_black_24dp)
                .into(imgProfilePic)
            Toast.makeText(this, "Successfully fetched user info.", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "Error occurred when fetching user info.", Toast.LENGTH_SHORT)
                .show()
        })

        txtFollowerCount.text = getString(R.string.follower).format(randomNumber)
        val currSong = musicManager.currentSong
        if (currSong != null) {
            imgUserSongPreview.setImageResource(currSong.largeImageID)
            txtUserListSongName.text = currSong.title
            txtUserListArtistName.text = currSong.artist
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.run {
            putInt(STATE_FOLLOWER, randomNumber)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}