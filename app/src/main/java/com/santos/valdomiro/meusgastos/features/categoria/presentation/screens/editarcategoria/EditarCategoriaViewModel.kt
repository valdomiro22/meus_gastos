package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.editarcategoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.features.categoria.domain.usecase.GetOneCategoriaUseCase
import com.santos.valdomiro.meusgastos.features.categoria.domain.usecase.UpdateOneCategoriaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class EditarCategoriaViewModel @Inject constructor(
    private val updateOneCategoriaUseCase: UpdateOneCategoriaUseCase,
    private val getOneCategoriaUseCase: GetOneCategoriaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditarCategoriaState())
    val uiState = _uiState.asStateFlow()

    fun onNomeChanged(value: String) {
        _uiState.update { it.copy(nome = value, erroNome = null) }
    }

    fun buscarCategoria(categoriaId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erroGeral = null) }

            getOneCategoriaUseCase(categoriaId)
                .onSuccess { categoria ->
                    _uiState.update {
                        it.copy(
                            id = categoria.id,
                            nome = categoria.nome,
                            criadoEm = categoria.criadoEm
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = "Erro ao buscar categoria cim ID: $categoriaId"
                        )
                    }
                }
        }
    }

    fun editarCategoria() {
        val currentState = _uiState.value
        val nome = currentState.nome.trim()

        if (nome.isEmpty()) {
            _uiState.update { it.copy(erroNome = "Digite o nome") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, erroGeral = null) }

            val nome = currentState.nome
            val id = currentState.id
            val criadoEm = currentState.criadoEm ?: Instant.now()

            updateOneCategoriaUseCase(nome = nome, id = id, criadoEm = criadoEm)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = null,
                            isEditSuccess = true
                        )
                    }
                }
                .onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = erro.message ?: "Erro ao editar categoria com ID: $id"
                        )
                    }
                }
        }
    }
}