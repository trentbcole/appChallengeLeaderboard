package com.example.appchallengeleaderboard

import android.content.Context

object ScoreManager {
    private const val SCORE_PREF_KEY = "scores"

    // Save score to SharedPreferences
    fun saveScore(score: Score, context: Context) {
        val sharedPreferences = context.getSharedPreferences("leaderboard", Context.MODE_PRIVATE)
        val existingScores = loadScores(context).toMutableList()

        // Remove previous score if it exists
        existingScores.removeAll { it.playerName == score.playerName }

        // Add the new score
        existingScores.add(score)

        // Sort scores by scoreValue in descending order
        existingScores.sortByDescending { it.scoreValue }

        // Take only the top 3 scores
        val top3Scores = existingScores.take(3)

        // Save the top 3 scores back to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putStringSet(SCORE_PREF_KEY, top3Scores.map { "${it.playerName},${it.scoreValue}" }.toSet())
        editor.apply()
    }

    // Load scores from SharedPreferences
    fun loadScores(context: Context): List<Score> {
        val sharedPreferences = context.getSharedPreferences("leaderboard", Context.MODE_PRIVATE)
        val scoresStringSet = sharedPreferences.getStringSet(SCORE_PREF_KEY, emptySet()) ?: emptySet()
        return scoresStringSet.map { it.split(",") }.map { Score(it[0], it[1].toInt()) }
    }
}

