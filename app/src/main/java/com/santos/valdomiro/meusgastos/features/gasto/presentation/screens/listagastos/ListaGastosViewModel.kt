package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.listagastos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetAllGastosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaGastosViewModel @Inject constructor(
    private val getAllGastosUseCase: GetAllGastosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GastoEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        Log.d(TAG, "getAll: Buscar Gastos")
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllGastosUseCase()
                .onSuccess { listaGastos -> _uiState.value = UiState.Success(listaGastos) }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Erro ao carregar gastos")
                }
        }
    }

}