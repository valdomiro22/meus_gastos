package com.santos.valdomiro.meusgastos.features.categoria.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.santos.valdomiro.meusgastos.features.categoria.data.model.CategoriaLocalModel

@Dao
interface CategoriaDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(categoria: CategoriaLocalModel)

    @Update
    suspend fun update(categoria: CategoriaLocalModel)

    @Delete
    suspend fun delete(categoria: CategoriaLocalModel)

    @Query("SELECT * FROM categoria ORDER BY criadoEm DESC")
    suspend fun getAll(): List<CategoriaLocalModel>

    @Query("SELECT * FROM categoria WHERE id = :id LIMIT 1")
    suspend fun getOneById(id: String): CategoriaLocalModel?
}