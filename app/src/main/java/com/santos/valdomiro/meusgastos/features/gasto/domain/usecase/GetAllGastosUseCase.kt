package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import android.util.Log
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class GetAllGastosUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(): Result<List<GastoEntity>> {
        return repository.getAll()
    }

}