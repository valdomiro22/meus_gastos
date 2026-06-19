package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import java.time.LocalDate
import javax.inject.Inject

class GetResumoDoMesUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(data: LocalDate): Result<List<GastoEntity>> {
        val mes = data.month
        val ano = data.year

        val result = repository.getAll()
        return result.fold(
            onSuccess = { listaGastos ->
                val listaFiltrada = listaGastos
                    .filter { gasto -> gasto.data.year == ano && gasto.data.month == mes }

                Result.success(listaFiltrada)
            },
            onFailure = { exception -> Result.failure(exception) }
        )
    }

}