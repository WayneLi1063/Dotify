package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = Random.nextInt(1000, 1000000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtNumberPlays = findViewById<TextView>(R.id.txtNumberPlays)
        txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)

        imgPrev.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        imgPlay.setOnClickListener {
            randomNumber += 1
            txtNumberPlays.text = getString(R.string.number_plays).format(randomNumber)
        }

        imgNext.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        btnChangeUser.setOnClickListener {
            btnChangeUserSubmitClicked()
        }

        imgDivide.setOnLongClickListener {
            imgDivideSubmitClicked(txtNumberPlays)
            return@setOnLongClickListener true
        }
    }

    private fun btnChangeUserSubmitClicked() {
        if (btnChangeUser.text == getString(R.string.change_user)) {
            val userName = txtUserName.text.toString()
            txtUserName.visibility = View.INVISIBLE
            btnChangeUser.text = getString(R.string.apply)
            etUserName.setText(userName)
            etUserName.visibility = View.VISIBLE
        } else {
            val newUserName = etUserName.text.toString()
            if (newUserName.isNotEmpty()) {
                btnChangeUser.text = getString(R.string.change_user)
                etUserName.visibility = View.INVISIBLE
                txtUserName.text = newUserName
                txtUserName.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun imgDivideSubmitClicked(txtNumberPlays: TextView) {
        val myColor = ContextCompat.getColor(this, R.color.blue)
        txtNumberPlays.setTextColor(myColor)
        txtArtist.setTextColor(myColor)
        txtSongTitle.setTextColor(myColor)
        txtUserName.setTextColor(myColor)
    }

}
