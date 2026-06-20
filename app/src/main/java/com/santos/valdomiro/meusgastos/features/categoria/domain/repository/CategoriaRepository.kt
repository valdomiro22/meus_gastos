package com.santos.valdomiro.meusgastos.features.categoria.domain.repository

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity

interface CategoriaRepository {
    suspend fun insertCategoria(categoria: CategoriaEntity): Result<Unit>
    suspend fun updateCategoria(categoria: CategoriaEntity): Result<Unit>
    suspend fun deleteCategoria(categoria: CategoriaEntity): Result<Unit>
    suspend fun getOnById(categoriaId: String): Result<CategoriaEntity?>
    suspend fun getAll(): Result<List<CategoriaEntity>>
}