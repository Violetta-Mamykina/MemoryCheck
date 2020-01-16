package com.example.memorycheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startGameEasy = findViewById<Button>(R.id.startGameEasy)
        val startGameMedium = findViewById<Button>(R.id.startGameMedium)
        val startGameHard = findViewById<Button>(R.id.startGameHard)
        val intent = Intent(this@MainActivity, GameProcess::class.java)
        startGameEasy.setOnClickListener {
            intent.putExtra("level", 0)
            startActivity(intent)
        }
        startGameMedium.setOnClickListener {
            intent.putExtra("level", 1)
            startActivity(intent)
        }
        startGameHard.setOnClickListener {
            intent.putExtra("level", 2)
            startActivity(intent)
        }
    }
}
