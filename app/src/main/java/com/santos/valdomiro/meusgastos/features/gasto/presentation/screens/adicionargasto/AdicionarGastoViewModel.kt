package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.adicionargasto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.InsertGastoParams
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.InsertGastoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AdicionarGastoViewModel @Inject constructor(
    private val insertGastoUseCase: InsertGastoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdicionarGastoState())
    val uiState = _uiState.asStateFlow()

    fun onDescricaoChanged(value: String) {
        _uiState.update { it.copy(descricao = value, erroDescricao = null) }
    }

    fun onValorChanged(value: String) {
        _uiState.update { it.copy(valor = value, erroValor = null) }
    }

    fun onCategoriaIdChanged(categoria: CategoriaEntity) {
        _uiState.update { it.copy(categoriaId = categoria.id, erroCategoriaId = null) }
    }

    fun onObservacaoChanged(value: String) {
        _uiState.update { it.copy(observacao = value, erroObservacao = null) }
    }

    fun onDataChanged(value: LocalDate?) {
        _uiState.update { it.copy(data = value, erroData = null) }
    }

    fun inserirGasto() {
        val currentState = _uiState.value

        if (!validar(currentState)) return


        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erroGeral = null) }


            val valorDouble = currentState.valor.toDoubleOrNull()
                ?: run {
                    _uiState.update {
                        it.copy(isLoading = false, erroValor = "Valor inválido")
                    }
                    return@launch
                }

            val params = InsertGastoParams(
                descricao = currentState.descricao,
                valor = valorDouble,
                categoriaId = currentState.categoriaId,
                data = currentState.data ?: LocalDate.now(),
                observacao = currentState.observacao
            )

            insertGastoUseCase(params = params)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { erro ->
                    _uiState.update { it.copy(isLoading = false, erroGeral = erro.message) }
                }
        }
    }

    private fun validar(state: AdicionarGastoState): Boolean {
        var isValidos = true
        var newState = state

        if (state.descricao.isEmpty()) {
            isValidos = false
            newState = newState.copy(erroDescricao = "Digite a descrição")
        }

        if (state.valor.isEmpty()) {
            isValidos = false
            newState = newState.copy(erroValor = "Digite o valor")
        }

        _uiState.update { newState }
        return isValidos;
    }

}