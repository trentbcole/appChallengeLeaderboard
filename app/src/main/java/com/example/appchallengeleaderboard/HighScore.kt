package com.example.appchallengeleaderboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appchallengeleaderboard.databinding.ActivityMainBinding
import com.example.appchallengeleaderboard.Score


class HighScore: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var highScores: List<Score> = emptyList()


    lateinit var highScore1: TextView
    lateinit var highScore2: TextView
    lateinit var highScore3: TextView
    lateinit var retake: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.leaderboard)

        highScore1 = findViewById(R.id.highScore1)
        highScore2 = findViewById(R.id.highScore2)
        highScore3 = findViewById(R.id.highScore3)
        retake = findViewById(R.id.retakeButton)

        loadHighScores()

        retake.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }



    private fun loadHighScores() {
        // Load high scores from storage
        highScores = ScoreManager.loadScores(this)

        // Display the top three high scores, assuming there are at least three scores
        if (highScores.isNotEmpty()) {
            highScore1.text = "${highScores[0].playerName}: ${highScores[0].scoreValue}"
        }
        if (highScores.size > 1) {
            highScore2.text = "${highScores[1].playerName}: ${highScores[1].scoreValue}"
        }
        if (highScores.size > 2) {
            highScore3.text = "${highScores[2].playerName}: ${highScores[2].scoreValue}"
        }
    }
}