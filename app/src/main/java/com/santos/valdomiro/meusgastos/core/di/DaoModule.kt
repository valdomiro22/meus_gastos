package com.santos.valdomiro.meusgastos.core.di

import com.santos.valdomiro.meusgastos.core.database.MeusGastosDatabase
import com.santos.valdomiro.meusgastos.features.gasto.data.dao.GastoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideGastoDao(
        database: MeusGastosDatabase
    ): GastoDao {
        return database.GastoDao()
    }
}