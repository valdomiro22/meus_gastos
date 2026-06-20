package com.santos.valdomiro.meusgastos.features.categoria.domain.entity

import java.time.Instant

data class CategoriaEntity(
    val id: String,
    val nome: String,
    val criadoEm: Instant,
    val editadoEm: Instant? = null
)