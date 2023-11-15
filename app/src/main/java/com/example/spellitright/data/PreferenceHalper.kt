package com.example.spellitright.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyGamePreferences",
            Context.MODE_PRIVATE)

    fun getHighScore(): Int {
        return sharedPreferences.getInt("highScore", 0)
    }

    fun setHighScore(score: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("highScore", score)
        editor.apply()
    }
}