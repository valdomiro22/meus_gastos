package com.santos.valdomiro.meusgastos.features.gasto.domain.entity

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import java.time.Instant
import java.time.LocalDate

data class GastoDetalhado(
    val gastoId: String,
    val gastoDescricao: String,
    val gastoValor: Double,
    val categoriaId: String,
    val categoriaNome: String,
    val gastoData: LocalDate,
    val gastoObservacao: String? = null,
    val gastoCriadoEm: Instant,
)