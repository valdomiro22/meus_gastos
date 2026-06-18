package com.santos.valdomiro.meusgastos.features.gasto.data.repository

import com.santos.valdomiro.meusgastos.features.gasto.data.localdatasource.GastoLocalDataSource
import com.santos.valdomiro.meusgastos.features.gasto.data.mapper.toEntity
import com.santos.valdomiro.meusgastos.features.gasto.data.mapper.toLocalModel
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class GastoRepositoryImpl @Inject constructor(
    private val localDataSource: GastoLocalDataSource
) : GastoRepository {

    override suspend fun insertGasto(gasto: GastoEntity): Result<Unit> {
        return try {
            localDataSource.insertGasto(gasto = gasto.toLocalModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateGasto(gasto: GastoEntity): Result<Unit> {
        return try {
            localDataSource.updateGasto(gasto = gasto.toLocalModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGasto(gasto: GastoEntity): Result<Unit> {
        return try {
            localDataSource.deleteGasto(gasto = gasto.toLocalModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOnById(gastoId: String): Result<GastoEntity?> {
        return try {
            val localModel = localDataSource.getOnById(gastoId)
            val entity = localModel?.toEntity()
            Result.success(entity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<GastoEntity>> {
        return try {
            val listaLocalModel = localDataSource.getAll()
            val listaEntity = listaLocalModel.map { it.toEntity() }
            Result.success(listaEntity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}