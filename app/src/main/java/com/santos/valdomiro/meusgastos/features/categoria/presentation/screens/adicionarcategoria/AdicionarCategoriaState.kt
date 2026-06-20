package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.adicionarcategoria

data class AdicionarCategoriaState(
    val nome: String = "",

    val erroNome: String? = null,
    val erroGeral: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)