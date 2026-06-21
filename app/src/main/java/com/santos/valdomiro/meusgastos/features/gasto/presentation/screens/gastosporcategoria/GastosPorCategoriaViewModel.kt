package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.gastosporcategoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.DeleteOneGastoUseCase
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetGastosPorCategoriaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastosPorCategoriaViewModel @Inject constructor(
    private val getGastosPorCategoriaUseCase: GetGastosPorCategoriaUseCase,
    private val deleteOneGastoUseCase: DeleteOneGastoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GastoEntity>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _categoriaSelecionadaId = MutableStateFlow<String?>(null)
    val categoriaSelecionadaId = _categoriaSelecionadaId.asStateFlow()

    fun onCategoriaChanged(categoria: CategoriaEntity) {
        _categoriaSelecionadaId.value = categoria.id
        getAll(categoria.id)
    }

    fun getAll(categoriaId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getGastosPorCategoriaUseCase(categoriaId)
                .onSuccess { listaGastos ->
                    _uiState.value = UiState.Success(listaGastos)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao carregar gastos da categoria"
                    )
                }
        }
    }

    fun deletarGasto(gasto: GastoEntity) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            deleteOneGastoUseCase(gasto = gasto)
                .onSuccess {
                    val categoriaAtual = _categoriaSelecionadaId.value ?: gasto.categoriaId
                    getAll(categoriaAtual)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao deletar gasto"
                    )
                }
        }
    }
}