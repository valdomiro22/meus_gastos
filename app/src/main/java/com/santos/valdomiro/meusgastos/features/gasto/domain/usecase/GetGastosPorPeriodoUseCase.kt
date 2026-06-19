package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import java.time.LocalDate
import javax.inject.Inject

class GetGastosPorPeriodoUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(inicio: LocalDate, fim: LocalDate): Result<List<GastoEntity>> {
        if (inicio > fim) {
            return Result.failure(
                IllegalArgumentException("A data inicial não pode ser maior que a data final")
            )
        }

        val result = repository.getAll()
        return result.fold(
            onSuccess = { listaGastos ->
                val listaFiltrada = listaGastos
                    .filter { gasto -> gasto.data >= inicio && gasto.data <= fim }

                Result.success(listaFiltrada)
            },
            onFailure = { exception -> Result.failure(exception) }
        )
    }

}