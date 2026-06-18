package com.santos.valdomiro.meusgastos.core.helper

object StringHelper {

    fun horarioReferenteToInt(horarioReferente: String): Int {
        val horarioInt = horarioReferente.replace(":", "").toIntOrNull()
            ?: throw IllegalArgumentException("Erro ao formatar horario referente")
        return horarioInt
    }

}
