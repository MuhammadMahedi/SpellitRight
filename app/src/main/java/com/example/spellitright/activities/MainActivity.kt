package com.example.spellitright.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.spellitright.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        // Show the AlertDialog
        showExitDialog()
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton("Yes") { _, _ ->
            finish() // Close the activity
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}