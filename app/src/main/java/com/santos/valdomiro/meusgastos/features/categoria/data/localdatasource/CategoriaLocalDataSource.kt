package com.santos.valdomiro.meusgastos.features.categoria.data.localdatasource

import com.santos.valdomiro.meusgastos.features.categoria.data.model.CategoriaLocalModel

interface CategoriaLocalDataSource {
    suspend fun insertCategoria(catetoria: CategoriaLocalModel)
    suspend fun updateCategoria(catetoria: CategoriaLocalModel)
    suspend fun deleteCategoria(catetoria: CategoriaLocalModel)
    suspend fun getOnById(categoriaId: String): CategoriaLocalModel?
    suspend fun getAll(): List<CategoriaLocalModel>
}