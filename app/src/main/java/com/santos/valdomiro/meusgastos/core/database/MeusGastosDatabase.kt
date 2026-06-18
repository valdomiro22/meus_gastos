package com.santos.valdomiro.meusgastos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.santos.valdomiro.meusgastos.features.gasto.data.dao.GastoDao
import com.santos.valdomiro.meusgastos.features.gasto.data.model.GastoLocalModel

@Database(
    entities = [
        GastoLocalModel::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class MeusGastosDatabase : RoomDatabase() {
    abstract fun GastoDao(): GastoDao
}