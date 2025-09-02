package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvPlayerName: TextView = findViewById(R.id.tvPlayerName)
        val tvScore: TextView = findViewById(R.id.tvScore)
        val tvResultsSummary: TextView = findViewById(R.id.tvResultsSummary)
        val btnPlayAgain: Button = findViewById(R.id.btnPlayAgain)


        val playerName = intent.getStringExtra("PLAYER_NAME")
        val finalScore = intent.getIntExtra("FINAL_SCORE", 0) // 0 é um valor padrão caso o dado não seja encontrado.
        val resultsDetails = intent.getStringExtra("RESULTS_DETAILS")


        tvPlayerName.text = playerName
        tvScore.text = "Pontuaçmas ão Final: $finalScore"
        tvResultsSummary.text = resultsDetails


        btnPlayAgain.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}

