package com.santos.valdomiro.meusgastos.features.gasto.data.mapper

import com.santos.valdomiro.meusgastos.features.gasto.data.model.GastoLocalModel
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import java.time.Instant
import java.time.ZoneOffset

/** Converte LocalModel para Entity */
fun GastoLocalModel.toEntity() = GastoEntity(
    id = this.id,
    descricao = this.descricao,
    valor = this.valor,
    categoriaId = this.categoriaId,
    data = Instant.ofEpochMilli(this.data)
        .atZone(ZoneOffset.UTC)
        .toLocalDate(),
    observacao = this.observacao,
    criadoEm = Instant.ofEpochMilli(this.criadoEm),
    editadoEm = this.editadoEm?.let { Instant.ofEpochMilli(it) }
)

/** Converte Entity para LocalModel */
fun GastoEntity.toLocalModel() = GastoLocalModel(
    id = this.id,
    descricao = this.descricao,
    valor = this.valor,
    categoriaId = this.categoriaId,
    data = this.data
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli(),
    observacao = this.observacao,
    criadoEm = this.criadoEm.toEpochMilli(),
    editadoEm = this.editadoEm?.toEpochMilli()
)