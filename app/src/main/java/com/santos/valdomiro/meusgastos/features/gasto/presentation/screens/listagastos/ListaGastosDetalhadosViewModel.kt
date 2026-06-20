package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.listagastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoDetalhado
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.DeleteOneGastoUseCase
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetGastoDetalhadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaGastosDetalhadosViewModel @Inject constructor(
    private val getGastosDetalhadosUsecase: GetGastoDetalhadoUseCase,
    private val deleteOneGastoUseCase: DeleteOneGastoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GastoDetalhado>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAll() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getGastosDetalhadosUsecase()
                .onSuccess { listaGastos -> _uiState.value = UiState.Success(listaGastos) }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Erro ao carregar gastos detalhados")
                }
        }
    }

    fun deletarGasto(gastoDetalhado: GastoDetalhado) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val gasto = GastoEntity(
                id = gastoDetalhado.gastoId,
                descricao = gastoDetalhado.gastoDescricao,
                valor = gastoDetalhado.gastoValor,
                categoriaId = gastoDetalhado.categoriaId,
                data = gastoDetalhado.gastoData,
                observacao = gastoDetalhado.gastoObservacao,
                criadoEm = gastoDetalhado.gastoCriadoEm
            )

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