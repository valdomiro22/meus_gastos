package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.editargasto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.GetOneGastoUseCase
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.UpdateGastoParams
import com.santos.valdomiro.meusgastos.features.gasto.domain.usecase.UpdateGastoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditarGastoViewModel @Inject constructor(
    private val updateGastoUseCase: UpdateGastoUseCase,
    private val getOneGastoUseCase: GetOneGastoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditarGastoState())
    val uiState = _uiState.asStateFlow()

    fun buscarGasto(gastoId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erroGeral = null) }

            getOneGastoUseCase(gastoId)
                .onSuccess { gasto ->
                    _uiState.update {
                        it.copy(
                            id = gasto.id,
                            descricao = gasto.descricao,
                            valor = gasto.valor.toString(),
                            categoriaId = gasto.categoriaId,
                            data = gasto.data,
                            observacao = gasto.observacao,
                            criadoEm = gasto.criadoEm,
                        )

                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = "Erro ao buscar gasto com id: $gastoId"
                        )
                    }
                }

        }
    }

    fun onDescricaoChanged(value: String) {
        _uiState.update { it.copy(descricao = value, erroDescricao = null) }
    }

    fun onValorChanged(value: String) {
        _uiState.update { it.copy(valor = value, erroValor = null) }
    }

    fun onCategoriaChanged(categoria: CategoriaEntity) {
        _uiState.update { it.copy(categoriaId = categoria.id, categoriaNome = categoria.nome, erroCategoria = null) }
    }

    fun onObservacaoChanged(value: String) {
        _uiState.update { it.copy(observacao = value, erroObservacao = null) }
    }

    fun onDataChanged(value: LocalDate?) {
        _uiState.update { it.copy(data = value, erroData = null) }
    }

    fun editarGasto() {
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

            val params = UpdateGastoParams(
                id = currentState.id,
                descricao = currentState.descricao,
                valor = valorDouble,
                categoriaId = currentState.categoriaId!!,
                categoriaNome = currentState.categoriaNome!!,
                data = currentState.data ?: LocalDate.now(),
                observacao = currentState.observacao,
                criadoEm = currentState.criadoEm ?: Instant.now()
            )


            updateGastoUseCase(params)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = null,
                            isEditSuccess = true
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = error.message ?: "Erro ao editar gasto"
                        )
                    }
                }
        }
    }

    private fun validar(state: EditarGastoState): Boolean {
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

        if (state.categoriaId == null || state.categoriaNome == null ) {
            isValidos = false
            newState = newState.copy(erroCategoria = "Selecione uma categoria")
        }

        _uiState.update { newState }
        return isValidos;
    }

}