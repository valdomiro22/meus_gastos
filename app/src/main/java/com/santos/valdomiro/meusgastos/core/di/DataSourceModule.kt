package com.santos.valdomiro.meusgastos.core.di

import com.santos.valdomiro.meusgastos.features.categoria.data.localdatasource.CategoriaLocalDataSource
import com.santos.valdomiro.meusgastos.features.categoria.data.localdatasource.CategoriaLocalDataSourceImpl
import com.santos.valdomiro.meusgastos.features.gasto.data.localdatasource.GastoLocalDataSource
import com.santos.valdomiro.meusgastos.features.gasto.data.localdatasource.GastoLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    // Gasto
    @Binds
    @Singleton
    abstract fun bindBastoLocalDataSource(impl: GastoLocalDataSourceImpl): GastoLocalDataSource

    // Categoria
    @Binds
    @Singleton
    abstract fun bindCategoriaLocalDataSource(impl: CategoriaLocalDataSourceImpl): CategoriaLocalDataSource

}