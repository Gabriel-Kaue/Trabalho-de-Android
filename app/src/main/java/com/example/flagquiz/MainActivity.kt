package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun iniciarJogo(view: View){
        val jogador1 = findViewById<EditText>(R.id.Jogador1).text.toString()

        if (jogador1.isEmpty()){
            findViewById<EditText>(R.id.Jogador1).error = "Por favor insira um nome"
            return
        }
        val intent = Intent(this, QuizActivity::class.java)

        intent.putExtra("jogador1", jogador1)
        startActivity(intent)
    }

}