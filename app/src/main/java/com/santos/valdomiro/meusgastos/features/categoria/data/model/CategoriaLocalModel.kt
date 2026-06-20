package com.santos.valdomiro.meusgastos.features.categoria.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "categoria")
data class CategoriaLocalModel(
    @PrimaryKey
    val id: String,

    val nome: String,
    val criadoEm: Long,
    val editadoEm: Long? = null
)