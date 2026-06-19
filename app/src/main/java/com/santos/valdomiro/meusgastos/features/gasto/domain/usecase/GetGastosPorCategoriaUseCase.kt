package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class GetGastosPorCategoriaUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(categoriaId: String): Result<List<GastoEntity>> {
        val result = repository.getAll();
        return result.fold(
            onSuccess = { gastos ->
                val gastosFiltrados = gastos.filter { gasto -> gasto.categoriaId == categoriaId }
                Result.success(gastosFiltrados)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

}