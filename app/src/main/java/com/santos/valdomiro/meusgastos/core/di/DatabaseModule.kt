package com.santos.valdomiro.meusgastos.core.di

import android.content.Context
import androidx.room.Room
import com.santos.valdomiro.meusgastos.core.database.MeusGastosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): MeusGastosDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MeusGastosDatabase::class.java,
            name = "meus_gastos_database"
        )
            .build()
    }
}