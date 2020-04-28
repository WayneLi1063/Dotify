package com.example.dotify

//import android.os.Bundle
//import android.view.MenuItem
//import android.view.View
//import android.widget.TextView
//import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlin.random.Random

class MainActivity : AppCompatActivity() {

//    private var randomNumber = Random.nextInt(1000, 1000000000)
//
//    companion object {
//        const val TITLE_KEY = "TITLE_KEY"
//        const val ARTIST_KEY = "ARTIST_KEY"
//        const val ALBUM_IMG_KEY = "ALBUM_IMG_KEY"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)
//        txtSongTitle.text = intent.getStringExtra(TITLE_KEY)
//        txtArtist.text = intent.getStringExtra(ARTIST_KEY)
//        imgDivide.setImageResource(intent.getIntExtra(ALBUM_IMG_KEY, -1))
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        imgPrev.setOnClickListener {
//            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
//        }
//
//        imgPlay.setOnClickListener {
//            randomNumber += 1
//            txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)
//        }
//
//        imgNext.setOnClickListener {
//            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
//        }
//
//        btnChangeUser.setOnClickListener {
//            btnChangeUserSubmitClicked()
//        }
//
//        imgDivide.setOnLongClickListener {
//            imgDivideSubmitClicked(txtNumberPlays)
//            return@setOnLongClickListener true
//        }
//    }
//
//    private fun btnChangeUserSubmitClicked() {
//        if (btnChangeUser.text == getString(R.string.change_user)) {
//            val userName = txtUserName.text.toString()
//            txtUserName.visibility = View.INVISIBLE
//            btnChangeUser.text = getString(R.string.apply)
//            etUserName.setText(userName)
//            etUserName.visibility = View.VISIBLE
//        } else {
//            val newUserName = etUserName.text.toString()
//            if (newUserName.isNotEmpty()) {
//                btnChangeUser.text = getString(R.string.change_user)
//                etUserName.visibility = View.INVISIBLE
//                txtUserName.text = newUserName
//                txtUserName.visibility = View.VISIBLE
//            } else {
//                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun imgDivideSubmitClicked(txtNumberPlays: TextView) {
//        val myColor = ContextCompat.getColor(this, R.color.blue)
//        txtNumberPlays.setTextColor(myColor)
//        txtArtist.setTextColor(myColor)
//        txtSongTitle.setTextColor(myColor)
//        txtUserName.setTextColor(myColor)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            finish()
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

}
