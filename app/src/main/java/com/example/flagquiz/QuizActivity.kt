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
        Pais("Italia", R.drawable.flag_italia),
        Pais("Jamaica", R.drawable.flag_jamaica),
        Pais("Marrocos", R.drawable.flag_marrocos),
        Pais("Russia", R.drawable.flag_russia),
        Pais("Suecia", R.drawable.flag_suecia)
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
    val resumoRodada = mutableListOf<String>()


    lateinit var textViewContador: TextView
    lateinit var imgFlag: ImageView
    lateinit var editTextResposta: EditText
    lateinit var buttonVerificar: Button
    lateinit var buttonProxima: Button

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        user = intent.getParcelableExtra<User>("user")!!

        if (user == null) {
            Toast.makeText(this, "Erro ao carregar dados do jogador", Toast.LENGTH_LONG).show()
            finish()
            return
        }

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
        val numeroPergunta = indiceAtual + 1

        if (resposta.equals(paisCorreto.nome, ignoreCase = true)) {
            pontos = pontos + 20
            resumoRodada.add("$numeroPergunta: Acertou! ${paisCorreto.nome}")
        } else {
            resumoRodada.add("$numeroPergunta: Você digitou $resposta e a resposta era: ${paisCorreto.nome}")
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

        user.pontos = this.pontos

        val intent = Intent(this, ResultActivity::class.java)

        //intents para passar os parametros
        intent.putExtra("PLAYER_NAME", user.nome)
        intent.putExtra("FINAL_SCORE", user.pontos)
        intent.putExtra("RESULTS_DETAILS", ArrayList(resumoRodada))


        startActivity(intent)
        finish()

    }
}