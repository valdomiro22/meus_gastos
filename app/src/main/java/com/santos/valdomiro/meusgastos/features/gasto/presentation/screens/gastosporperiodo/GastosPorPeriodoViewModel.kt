package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.gastosporperiodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.DeleteOneGastoUseCase
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetGastosPorPeriodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GastosPorPeriodoViewModel @Inject constructor(
    private val getGastosPorPeriodoUseCase: GetGastosPorPeriodoUseCase,
    private val deleteOneGastoUseCase: DeleteOneGastoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GastoEntity>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _dataInicioSelecionada = MutableStateFlow<LocalDate?>(null)
    val dataInicioSelecionada = _dataInicioSelecionada.asStateFlow()

    private val _dataFimSelecionada = MutableStateFlow<LocalDate?>(null)
    val dataFimSelecionada = _dataFimSelecionada.asStateFlow()

    private val _erroInicio = MutableStateFlow<String?>(null)
    val erroInicio = _erroInicio.asStateFlow()

    private val _erroFim = MutableStateFlow<String?>(null)
    val erroFim = _erroFim.asStateFlow()

    fun onInicioChanged(inicio: LocalDate?) {
        _dataInicioSelecionada.value = inicio
        _erroInicio.value = null
    }

    fun onFimChanged(fim: LocalDate?) {
        _dataFimSelecionada.value = fim
        _erroFim.value = null
    }

    fun buscarGastos() {
        val inicio = _dataInicioSelecionada.value
        val fim = _dataFimSelecionada.value

        _erroInicio.value = null
        _erroFim.value = null

        if (inicio == null) {
            _erroInicio.value = "Selecione a data de início"
            return
        }

        if (fim == null) {
            _erroFim.value = "Selecione a data de fim"
            return
        }

        getGastos(inicio = inicio, fim = fim)
    }

    private fun getGastos(inicio: LocalDate, fim: LocalDate) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getGastosPorPeriodoUseCase(inicio = inicio, fim = fim)
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
                    val inicio = _dataInicioSelecionada.value
                    val fim = _dataFimSelecionada.value

                    if (inicio != null && fim != null) getGastos(inicio = inicio, fim = fim)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao deletar gasto"
                    )
                }
        }
    }

}