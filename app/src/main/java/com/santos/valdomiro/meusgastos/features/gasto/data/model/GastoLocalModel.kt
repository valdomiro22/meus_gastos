package com.santos.valdomiro.meusgastos.features.gasto.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "gasto")
data class GastoLocalModel(
    @PrimaryKey
    val id: String,

    val descricao: String,
    val valor: Double,
    val categoriaId: String? = null,
    val data: Long,
    val observacao: String? = null,
    val criadoEm: Long,
    val editadoEm: Long? = null
)