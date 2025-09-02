package com.example.flagquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flagquiz.R.id.editTextResposta

data class Pais(val nome: String, val imagemId : Int)
class QuizActivity : AppCompatActivity() {

    val listaDePaises = listOf(
        Pais("Brasil", R.drawable.flag_brazil),
        Pais("Argentina", R.drawable.flag_argentina),
        Pais("Barbados", R.drawable.flag_barbados),
        Pais("Angola", R.drawable.flag_angola),
        Pais("China", R.drawable.flag_china),
        Pais("Cuba", R.drawable.flag_cuba),
        Pais("Egito", R.drawable.flag_egito),
        Pais("Gana", R.drawable.flag_ghana),
        Pais("Itália", R.drawable.flag_italia),
        Pais("Jamaica", R.drawable.flag_jamaica),
        Pais("Marrocos", R.drawable.flag_marrocos),
        Pais("Rússia", R.drawable.flag_russia),
        Pais("Suécia", R.drawable.flag_suecia)
    )
    val flags = listOf(
        R.drawable.flag_brazil,
        R.drawable.flag_argentina,
        R.drawable.flag_barbados,
        R.drawable.flag_angola,
        R.drawable.flag_china,
        R.drawable.flag_cuba,
        R.drawable.flag_egito,
        R.drawable.flag_ghana,
        R.drawable.flag_italia,
        R.drawable.flag_jamaica,
        R.drawable.flag_marrocos,
        R.drawable.flag_russia,
        R.drawable.flag_suecia
    )

    lateinit var paisesRodada: List<Pais>
    var indiceAtual = 0
    var pontos = 0


    lateinit var textViewContador: TextView
    lateinit var imgFlag: ImageView
    lateinit var editTextResposta: EditText
    lateinit var buttonVerificar: Button
    lateinit var buttonProxima: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textViewContador = findViewById(R.id.textViewContador)
        imgFlag = findViewById(R.id.imgFlag)
        editTextResposta = findViewById(R.id.editTextResposta)
        buttonVerificar = findViewById(R.id.buttonVerificar)
        buttonProxima = findViewById(R.id.buttonProxima)

        buttonVerificar.setOnClickListener {
            verificarResposta()
        }
        buttonProxima.setOnClickListener {
            proximaPergunta()
        }

        novaRodada()

    }

    fun novaRodada() {
        paisesRodada = listaDePaises.shuffled().take(5)
        indiceAtual = 0
        pontos = 0
        exibirPerguntaAtual()
    }

    fun exibirPerguntaAtual() {
        val paisAtual = paisesRodada[indiceAtual]

        textViewContador.text = "Pergunta ${indiceAtual + 1} de ${paisesRodada.size}"
        imgFlag.setImageResource(paisAtual.imagemId)
        editTextResposta.text.clear()

        // Controla a visibilidade e estado dos botões
        buttonVerificar.isEnabled = true
        editTextResposta.isEnabled = true
        buttonProxima.visibility = View.GONE
    }

    fun verificarResposta() {
        val resposta = editTextResposta.text.toString()
        val paisCorreto = paisesRodada[indiceAtual]

        if (resposta.equals(paisCorreto.nome, ignoreCase = true)) {
            pontos++
            Toast.makeText(this, "Acertou!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Errado! A resposta era ${paisCorreto.nome}", Toast.LENGTH_SHORT).show()
        }

        buttonVerificar.isEnabled = false
        editTextResposta.isEnabled = false
        buttonProxima.visibility = View.VISIBLE
    }

    fun proximaPergunta() {
        indiceAtual++

        if (indiceAtual < paisesRodada.size) {
            exibirPerguntaAtual()
        } else {
            finalizarJogo()
        }
    }

    fun finalizarJogo() {
        val intent = Intent(this, ResultActivity::class.java)

        //intents para passar os parametros
        //intent.putExtra("pontuacao_final", pontos)

        startActivity(intent)
        finish()

    }
}