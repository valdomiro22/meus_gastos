package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.resumomes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetResumoDoMesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ResumoDoMesViewModel @Inject constructor(
    private val getResumoDoMesUseCase: GetResumoDoMesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GastoEntity>>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _resumoState = MutableStateFlow(ResumoDoMesState())
    val resumoState = _resumoState.asStateFlow()

    fun getAll(data: LocalDate) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getResumoDoMesUseCase(data = data)
                .onSuccess { listaGastos ->
                    _uiState.value = UiState.Success(listaGastos)
                    gerarResumo(listaGastos)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Erro ao carregar gastos do mês"
                    )
                }
        }
    }

    private fun gerarResumo(listaGastos: List<GastoEntity>) {
        val maiorGasto = listaGastos.maxByOrNull { gasto -> gasto.valor }
        val menorGasto = listaGastos.minByOrNull { gasto -> gasto.valor }

        val categoriaMaisUtilizada = listaGastos
            .groupBy { gasto -> gasto.categoriaNome }
            .maxByOrNull { (_, gastosDaCategoria) ->
                gastosDaCategoria.size
            }

        _resumoState.value = ResumoDoMesState(
            totalGasto = listaGastos.sumOf { gasto -> gasto.valor },
            quantidadeGastos = listaGastos.size,

            maiorValorGasto = maiorGasto?.valor ?: 0.0,
            menorValorGasto = menorGasto?.valor ?: 0.0,

            categoriaDoMaiorValor = maiorGasto?.categoriaNome ?: "",
            categoriaDoMenorValor = menorGasto?.categoriaNome ?: "",

            categoriaMaisUtilizada = categoriaMaisUtilizada?.key ?: "",
            quantidadeDeVezesCategoriaMaisUtilizada = categoriaMaisUtilizada?.value?.size ?: 0
        )
    }

}