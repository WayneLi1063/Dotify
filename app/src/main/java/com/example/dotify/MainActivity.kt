package com.example.dotify

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import kotlin.random.nextInt

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
            if (btnChangeUser.text == "Change User") {
                txtUserName.visibility = View.INVISIBLE
                val userName = txtUserName.text
                btnChangeUser.text = "Apply"
                etUserName.setText(userName)
                etUserName.visibility = View.VISIBLE
            } else {
                val newUserName = etUserName.text.toString()
            }
        }
    }
}
