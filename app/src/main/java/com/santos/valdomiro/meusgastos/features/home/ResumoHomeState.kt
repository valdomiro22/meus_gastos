package com.santos.valdomiro.meusgastos.features.home

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity

data class ResumoHomeState(
    val totalGastoMes: Double = 0.0,
    val quantidadeGastosMes: Int = 0,
    val ultimosGastos: List<GastoEntity> = emptyList(),
    val isLoading: Boolean = false,
    val erro: String? = null
)