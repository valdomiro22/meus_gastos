package com.santos.valdomiro.meusgastos.features.categoria.data.mapper

import com.santos.valdomiro.meusgastos.features.categoria.data.model.CategoriaLocalModel
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import java.time.Instant

/** Converte LocalModel para Entity */
fun CategoriaLocalModel.toEntity() = CategoriaEntity(
    id = this.id,
    nome = this.nome,
    criadoEm = Instant.ofEpochMilli(this.criadoEm),
    editadoEm = this.editadoEm?.let { Instant.ofEpochMilli(it) },
)

/** Converte Entity para LocalModel */
fun CategoriaEntity.toLocalModel() = CategoriaLocalModel(
    id = this.id,
    nome = this.nome,
    criadoEm = this.criadoEm.toEpochMilli(),
    editadoEm = this.editadoEm?.toEpochMilli(),
)
