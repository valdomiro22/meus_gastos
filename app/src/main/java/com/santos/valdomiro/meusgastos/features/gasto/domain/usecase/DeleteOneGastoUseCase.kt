package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class DeleteOneGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(gasto: GastoEntity): Result<Unit> {
        if (gasto.id.isBlank()) {
            return Result.failure(IllegalArgumentException("ID do gasto não pode estar vazio"))
        }

        return repository.deleteGasto(gasto)
    }

}