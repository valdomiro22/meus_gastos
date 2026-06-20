package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.adicionarcategoria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santos.valdomiro.meusgastos.features.categoria.domain.usecase.InsertOneCategoriaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdicionarCategoriaViewModel @Inject constructor(
    private val insertOneCategoriaUseCase: InsertOneCategoriaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdicionarCategoriaState())
    val uiState = _uiState.asStateFlow()

    fun onNomeChanged(value: String) {
        _uiState.update { it.copy(nome = value, erroNome = null) }
    }

    fun adicionarCategoria() {
        val currentState = _uiState.value
        val nome = currentState.nome.trim()

        if (nome.isEmpty()) {
            _uiState.update { it.copy(erroNome = "Digite o nome") }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, erroGeral = null)
            }

            insertOneCategoriaUseCase(nome = nome)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { erro ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            erroGeral = erro.message ?: "Erro ao adicionar categoria"
                        )
                    }
                }
        }
    }

}