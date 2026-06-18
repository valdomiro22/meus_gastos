package com.santos.valdomiro.meusgastos.features.gasto.domain.repository

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity

interface GastoRepository {
    suspend fun insertGasto(gasto: GastoEntity): Result<Unit>
    suspend fun updateGasto(gasto: GastoEntity): Result<Unit>
    suspend fun deleteGasto(gasto: GastoEntity): Result<Unit>
    suspend fun getOnById(gastoId: String): Result<GastoEntity?>
    suspend fun getAll(): Result<List<GastoEntity>>
}