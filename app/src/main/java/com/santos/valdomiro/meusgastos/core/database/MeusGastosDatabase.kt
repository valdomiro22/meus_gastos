package com.santos.valdomiro.meusgastos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santos.valdomiro.meusgastos.features.categoria.data.dao.CategoriaDao
import com.santos.valdomiro.meusgastos.features.categoria.data.model.CategoriaLocalModel
import com.santos.valdomiro.meusgastos.features.gasto.data.dao.GastoDao
import com.santos.valdomiro.meusgastos.features.gasto.data.model.GastoLocalModel

@Database(
    entities = [
        GastoLocalModel::class,
        CategoriaLocalModel::class,
    ],
    version = 2,
    exportSchema = true
)
abstract class MeusGastosDatabase : RoomDatabase() {
    abstract fun GastoDao(): GastoDao
    abstract fun CategoriaDao(): CategoriaDao
}