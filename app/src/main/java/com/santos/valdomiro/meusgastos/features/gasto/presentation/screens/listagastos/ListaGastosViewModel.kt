package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.listagastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.DeleteOneGastoUseCase
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetAllGastosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaGastosViewModel @Inject constructor(
    private val getAllGastosUseCase: GetAllGastosUseCase,
    private val deleteOneGastoUseCase: DeleteOneGastoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GastoEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllGastosUseCase()
                .onSuccess { listaGastos -> _uiState.value = UiState.Success(listaGastos) }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Erro ao carregar gastos")
                }
        }
    }

    fun deletarGasto(gasto: GastoEntity) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            deleteOneGastoUseCase(gasto = gasto)
                .onSuccess { getAll() }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao deletar gasto"
                    )
                }
        }
    }

}