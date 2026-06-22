package com.santos.valdomiro.meusgastos.core.util

import java.time.LocalDate

fun formatarMoeda(valor: Double): String {
//    return "R$ %.2f".format(valor).replace(".", ",")
    return "%.2f".format(valor).replace(".", ",")
}

fun mesAtualFormatado(): String {
    val hoje = LocalDate.now()

    val nomeMes = when (hoje.monthValue) {
        1 -> "Janeiro"
        2 -> "Fevereiro"
        3 -> "Março"
        4 -> "Abril"
        5 -> "Maio"
        6 -> "Junho"
        7 -> "Julho"
        8 -> "Agosto"
        9 -> "Setembro"
        10 -> "Outubro"
        11 -> "Novembro"
        else -> "Dezembro"
    }

    return "$nomeMes de ${hoje.year}"
}

fun mesAtualFormatadoSemAno(): String {
    val hoje = LocalDate.now()

    val nomeMes = when (hoje.monthValue) {
        1 -> "Janeiro"
        2 -> "Fevereiro"
        3 -> "Março"
        4 -> "Abril"
        5 -> "Maio"
        6 -> "Junho"
        7 -> "Julho"
        8 -> "Agosto"
        9 -> "Setembro"
        10 -> "Outubro"
        11 -> "Novembro"
        else -> "Dezembro"
    }

    return nomeMes
}