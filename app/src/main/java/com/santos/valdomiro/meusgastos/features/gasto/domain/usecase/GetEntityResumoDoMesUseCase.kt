package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import com.santos.valdomiro.meusgastos.features.home.ResumoDoMesEntity
import java.time.LocalDate
import javax.inject.Inject

class GetEntityResumoDoMesUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(data: LocalDate): Result<ResumoDoMesEntity> {
        val mes = data.month
        val ano = data.year

        val result = repository.getAll()

        return result.fold(
            onSuccess = { listaGastos ->
                val gastosDoMes = listaGastos.filter { gasto ->
                    gasto.data.year == ano && gasto.data.month == mes
                }

                val totalGastoMes = gastosDoMes.sumOf { gasto ->
                    gasto.valor
                }

                val quantidadeGastosMes = gastosDoMes.size

                val ultimosGastos = gastosDoMes
                    .sortedByDescending { gasto -> gasto.data }
                    .take(5)

                Result.success(
                    ResumoDoMesEntity(
                        totalGastoMes = totalGastoMes,
                        quantidadeGastosMes = quantidadeGastosMes,
                        ultimosGastos = ultimosGastos
                    )
                )
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}