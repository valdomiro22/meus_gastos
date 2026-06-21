package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.totalporcategoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.categoria.domain.usecase.GetAllCategoriasUseCase
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetGastosPorCategoriaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TotalPorCategoriaViewModel @Inject constructor(
    private val getGastosPorCategoriaUseCase: GetGastosPorCategoriaUseCase,
    private val getAllCategoriasUseCase: GetAllCategoriasUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<TotalPorCategoriaItemState>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()


    fun getGastos() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllCategoriasUseCase()
                .onSuccess { listaCategorias ->
                    val listaTotalPorCategoria = listaCategorias.map { categoriaEntity ->
                        val gastosPorCategoria = getGastosPorCategoriaUseCase(
                            categoriaId = categoriaEntity.id
                        ).getOrElse { emptyList() }

                        TotalPorCategoriaItemState(
                            categoriaId = categoriaEntity.id,
                            categoriaNome = categoriaEntity.nome,
                            totalGasto = gastosPorCategoria.sumOf { gasto -> gasto.valor }
                        )

                    }
                    _uiState.value = UiState.Success(listaTotalPorCategoria)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao buscar todas as categorias"
                    )
                }
        }
    }
}