package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import android.util.Log
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import java.time.LocalDate
import javax.inject.Inject

class GetResumoDoDiaUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(data: LocalDate): Result<List<GastoEntity>> {
        val dia = data.dayOfMonth
        val mes = data.month
        val ano = data.year

        val result = repository.getAll()
        return result.fold(
            onSuccess = { listaGastos ->
                val listaFiltrada = listaGastos
                    .filter { gasto ->
                        gasto.data.year == ano &&
                        gasto.data.month == mes &&
                        gasto.data.dayOfMonth == dia
                    }

                Log.d(TAG, "invoke: dia buscado: $dia/$mes/$ano")
                Log.d(TAG, "invoke: dia do gasto: ${listaGastos.first().data}, valor: ${listaGastos.first().valor}")
                Log.d(TAG, "invoke: dia do gasto: ${listaGastos.last().data}, valor: ${listaGastos.last().valor}")

                Result.success(listaFiltrada)
            },
            onFailure = { exception -> Result.failure(exception) }
        )
    }

}