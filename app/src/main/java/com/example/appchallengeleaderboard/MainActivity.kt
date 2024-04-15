package com.example.appchallengeleaderboard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appchallengeleaderboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions = arrayOf("What is the name of my dog?",
        "What is the name of my cat?",
        "How many cats do I own?")

    private val options = arrayOf(arrayOf("Bark", "Joe", "Gus"),
        arrayOf("Milky", "Dusty", "Galaxy"),
        arrayOf("3","2","4")
    )

    private val correctAnswers = arrayOf(2, 2, 0)

    private var currentQuestionIndex = 0

    companion object {
        var score = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.option1Button.setOnClickListener {
            checkAnswer(0)
        }

        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }

        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }

        binding.restartButton.setOnClickListener {
            restartQuiz()
        }

        binding.checkLeaders.setOnClickListener {
            val intent = Intent(this, HighScore::class.java)
            startActivity(intent)
        }
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when(buttonIndex){
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when(buttonIndex){
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
            binding.option1Button.setBackgroundColor(Color.rgb(50,59,96))
            binding.option2Button.setBackgroundColor(Color.rgb(50,59,96))
            binding.option3Button.setBackgroundColor(Color.rgb(50,59,96))

    }

    private fun showResults() {
        saveScore()
        Toast.makeText(this, "Your score: $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        binding.restartButton.isEnabled = true
    }

    private fun displayQuestion() {
        binding.questionText.text = questions[currentQuestionIndex]
        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]
        resetButtonColors()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex){
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.questionText.postDelayed({displayQuestion()}, 1000)
        } else {
            showResults()
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        displayQuestion()
        binding.restartButton.isEnabled = false
    }

    private fun saveScore() {
        val playerName = "Player1"
        val newScore = Score(playerName, score)
        ScoreManager.saveScore(newScore, this)
    }


}