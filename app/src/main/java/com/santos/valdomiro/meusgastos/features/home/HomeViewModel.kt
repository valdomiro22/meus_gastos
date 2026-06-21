package com.santos.valdomiro.meusgastos.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetEntityResumoDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEntityResumoDoMesUseCase: GetEntityResumoDoMesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResumoHomeState())
    val uiState = _uiState.asStateFlow()

    fun carregarResumo(data: LocalDate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                erro = null
            )

            getEntityResumoDoMesUseCase(data)
                .onSuccess { resumo ->
                    _uiState.value = _uiState.value.copy(
                        totalGastoMes = resumo.totalGastoMes,
                        quantidadeGastosMes = resumo.quantidadeGastosMes,
                        ultimosGastos = resumo.ultimosGastos,
                        isLoading = false,
                        erro = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        erro = exception.message ?: "Erro ao carregar resumo"
                    )
                }
        }
    }
}