package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class GetOneGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(gastoId: String): Result<GastoEntity> {
        if (gastoId.isBlank()) {
            return Result.failure(IllegalArgumentException("gastoId não pode estar vazio"))
        }

        val result = repository.getOnById(gastoId)

        return  result.fold(
            onSuccess = { gasto ->
                if (gasto == null) {
                    return Result.failure(IllegalArgumentException("Erro ao buscar gasto com o ID $gastoId"))
                }

                Result.success(gasto)
            },
            onFailure = {
                Result.failure(IllegalArgumentException("Erro ao buscar gasto com o ID $gastoId"))
            }
        )
    }

}