package com.santos.valdomiro.meusgastos.features.home.components

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity

data class ResumoDoMesEntity(
    val totalGastoMes: Double,
    val quantidadeGastosMes: Int,
    val ultimosGastos: List<GastoEntity>
)