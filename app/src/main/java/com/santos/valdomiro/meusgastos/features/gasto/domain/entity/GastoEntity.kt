package com.santos.valdomiro.meusgastos.features.gasto.domain.entity

import java.time.Instant
import java.time.LocalDate

data class GastoEntity(
    val id: String,
    val descricao: String,
    val valor: Double,
    val categoriaId: String?,
    val data: LocalDate,
    val observacao: String?,
    val criadoEm: Instant,
    val editadoEm: Instant?
)