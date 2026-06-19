package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class GetTotalPorCategoriaUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(categoriaId: String): Result<Double> {
        val result = repository.getAll();
        return result.fold(
            onSuccess = { gastos ->
                val totalGasto = gastos
                    .filter { gasto -> gasto.categoriaId == categoriaId }
                    .sumOf { gasto -> gasto.valor }

                Result.success(totalGasto)
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }

}