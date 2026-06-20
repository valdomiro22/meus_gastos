package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.listacategorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.usecase.DeleteOneCategoriaUseCase
import com.santos.valdomiro.meusgastos.features.categoria.domain.usecase.GetAllCategoriasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaCategoriasViewModel @Inject constructor(
    private val getAllCategoriasUseCase: GetAllCategoriasUseCase,
    private val deleteOneCategoriaUseCase: DeleteOneCategoriaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CategoriaEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllCategoriasUseCase()
                .onSuccess { listaGastos -> _uiState.value = UiState.Success(listaGastos) }
                .onFailure { exception ->
                    _uiState.value =
                        UiState.Error(exception.message ?: "Erro ao carregar categorias")
                }
        }
    }

    fun deltarCategoria(categoria: CategoriaEntity) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            deleteOneCategoriaUseCase(categoria)
                .onSuccess { getAll() }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao deletar categoria"
                    )
                }
        }
    }
}