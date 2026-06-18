package com.santos.valdomiro.meusgastos.features.gasto.data.localdatasource

import com.santos.valdomiro.meusgastos.features.gasto.data.model.GastoLocalModel

interface GastoLocalDataSource {
    suspend fun insertGasto(gasto: GastoLocalModel)
    suspend fun updateGasto(gasto: GastoLocalModel)
    suspend fun deleteGasto(gasto: GastoLocalModel)
    suspend fun getOnById(gastoId: String): GastoLocalModel?
    suspend fun getAll(): List<GastoLocalModel>
}