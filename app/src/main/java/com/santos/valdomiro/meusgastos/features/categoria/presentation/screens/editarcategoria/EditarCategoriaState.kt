 package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.editarcategoria

 import java.time.Instant

 data class EditarCategoriaState(
     val id: String = "",
     val nome: String = "",
     val criadoEm: Instant? = null,

     val erroNome: String? = null,
     val erroGeral: String? = null,

     val isLoading: Boolean = false,
     val isSuccess: Boolean = false,
     val isEditSuccess: Boolean = false,
 )