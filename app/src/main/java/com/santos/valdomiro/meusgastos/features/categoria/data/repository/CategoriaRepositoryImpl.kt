package com.santos.valdomiro.meusgastos.features.categoria.data.repository

import com.santos.valdomiro.meusgastos.features.categoria.data.localdatasource.CategoriaLocalDataSource
import com.santos.valdomiro.meusgastos.features.categoria.data.mapper.toEntity
import com.santos.valdomiro.meusgastos.features.categoria.data.mapper.toLocalModel
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import javax.inject.Inject

class CategoriaRepositoryImpl @Inject constructor(
    private val localDataSource: CategoriaLocalDataSource
) : CategoriaRepository {
    override suspend fun insertCategoria(categoria: CategoriaEntity): Result<Unit> {
        return try {
            localDataSource.insertCategoria(categoria.toLocalModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCategoria(categoria: CategoriaEntity): Result<Unit> {
        return try {
            localDataSource.updateCategoria(categoria.toLocalModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCategoria(categoria: CategoriaEntity): Result<Unit> {
        return try {
            localDataSource.deleteCategoria(categoria.toLocalModel())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOnById(categoriaId: String): Result<CategoriaEntity?> {
        return try {
            val localModel = localDataSource.getOnById(categoriaId)

            Result.success(localModel?.toEntity())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAll(): Result<List<CategoriaEntity>> {
        return try {
            val listaLocalModel = localDataSource.getAll()
            val listaConvertida = listaLocalModel.map { it.toEntity() }

            Result.success(listaConvertida)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}