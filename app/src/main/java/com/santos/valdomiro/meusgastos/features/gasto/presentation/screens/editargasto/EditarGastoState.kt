package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.editargasto

import java.time.Instant
import java.time.LocalDate

data class EditarGastoState(
    val id: String = "",
    val descricao: String = "",
    val valor: String = "",
    val categoriaId: String? = null,
    val categoriaNome: String? = null,
    val observacao: String? = null,
    val data: LocalDate? = LocalDate.now(),
    val criadoEm: Instant? = null,

    val erroDescricao: String? = null,
    val erroData: String? = null,
    val erroCategoria: String? = null,
    val erroValor: String? = null,
    val erroObservacao: String? = null,
    val erroGeral: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isEditSuccess: Boolean = false,
)
