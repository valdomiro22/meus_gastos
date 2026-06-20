package com.santos.valdomiro.meusgastos.core.di

import com.santos.valdomiro.meusgastos.features.categoria.data.repository.CategoriaRepositoryImpl
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import com.santos.valdomiro.meusgastos.features.gasto.data.repository.GastoRepositoryImpl
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Gasto
    @Binds
    @Singleton
    abstract fun bindGastoRepository(
        gastoRepositoryImpl: GastoRepositoryImpl
    ): GastoRepository

    // Categoria
    @Binds
    @Singleton
    abstract fun bindCategoriaRepository(
        categoriaRepositoryImpl: CategoriaRepositoryImpl
    ): CategoriaRepository
}