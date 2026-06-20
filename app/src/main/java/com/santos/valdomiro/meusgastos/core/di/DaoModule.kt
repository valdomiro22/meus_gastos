package com.santos.valdomiro.meusgastos.core.di

import com.santos.valdomiro.meusgastos.core.database.MeusGastosDatabase
import com.santos.valdomiro.meusgastos.features.categoria.data.dao.CategoriaDao
import com.santos.valdomiro.meusgastos.features.gasto.data.dao.GastoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    // Gasto
    @Provides
    @Singleton
    fun provideGastoDao(
        database: MeusGastosDatabase
    ): GastoDao {
        return database.GastoDao()
    }

    // Categoria
    @Provides
    @Singleton
    fun provideCategoriaDao(
        database: MeusGastosDatabase
    ): CategoriaDao {
        return database.CategoriaDao()
    }
}