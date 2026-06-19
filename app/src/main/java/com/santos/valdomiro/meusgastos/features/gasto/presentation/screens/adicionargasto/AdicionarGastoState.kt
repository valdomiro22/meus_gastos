package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.adicionargasto

import java.time.LocalDate

data class AdicionarGastoState(
    val descricao: String = "",
    val valor: String = "",
    val categoriaId: String? = null,
    val observacao: String? = null,
    val data: LocalDate? = LocalDate.now(),

    val erroDescricao: String? = null,
    val erroData: String? = null,
    val erroCategoriaId: String? = null,
    val erroValor: String? = null,
    val erroObservacao: String? = null,
    val erroGeral: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)
