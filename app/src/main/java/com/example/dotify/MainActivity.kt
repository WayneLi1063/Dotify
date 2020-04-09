package com.example.dotify

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtNumberplays = findViewById<TextView>(R.id.txtNumberPlays)
        txtNumberplays.text = "$randomNumber plays"

        imgPrev.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        imgPlay.setOnClickListener {
            randomNumber += 1
            txtNumberplays.text = "$randomNumber plays"
        }

        imgNext.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        btnChangeUser.setOnClickListener {
            if (btnChangeUser.text == "CHANGE USER") {
                val userName = txtUserName.text.toString()
                txtUserName.visibility = View.INVISIBLE
                btnChangeUser.text = "APPLY"
                etUserName.setText(userName)
                etUserName.visibility = View.VISIBLE
            } else {
                val newUserName = etUserName.text.toString()
                if (newUserName.isNotEmpty()) {
                    btnChangeUser.text = "CHANGE USER"
                    etUserName.visibility = View.INVISIBLE
                    txtUserName.text = newUserName
                    txtUserName.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }

        imgDivide.setOnLongClickListener {
            val myColor = ContextCompat.getColor(this, R.color.blue)
            txtNumberplays.setTextColor(myColor)
            txtArtist.setTextColor(myColor)
            txtSongTitle.setTextColor(myColor)
            txtUserName.setTextColor(myColor)
            return@setOnLongClickListener true
        }
    }
}
