package com.example.flagquiz

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val nome: String,
    var pontos: Int,
): Parcelable
