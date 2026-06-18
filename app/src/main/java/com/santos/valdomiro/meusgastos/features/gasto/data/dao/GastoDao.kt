package com.santos.valdomiro.meusgastos.features.gasto.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.santos.valdomiro.meusgastos.features.gasto.data.model.GastoLocalModel

@Dao
interface GastoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(gasto: GastoLocalModel)

    @Update
    suspend fun update(gasto: GastoLocalModel)

    @Delete
    suspend fun delete(gasto: GastoLocalModel)

    @Query("SELECT * FROM gasto WHERE id = :id LIMIT 1")
    suspend fun getOneById(id: String): GastoLocalModel?

    @Query("SELECT * FROM gasto ORDER BY data DESC")
    suspend fun getAll(): List<GastoLocalModel>
}