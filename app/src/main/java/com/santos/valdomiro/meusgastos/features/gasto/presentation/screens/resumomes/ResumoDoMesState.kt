package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.resumomes

data class ResumoDoMesState(
    val totalGasto: Double = 0.0,
    val quantidadeGastos: Int = 0,

    val maiorValorGasto: Double = 0.0,
    val menorValorGasto: Double = 0.0,

    val categoriaDoMaiorValor: String = "",
    val categoriaDoMenorValor: String = "",

    val categoriaMaisUtilizada: String = "",
    val quantidadeDeVezesCategoriaMaisUtilizada: Int = 0
)
