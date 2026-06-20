package com.santos.valdomiro.meusgastos.features.categoria.data.localdatasource

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.santos.valdomiro.meusgastos.core.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.meusgastos.core.exceptions.RegistroDuplicadoException
import com.santos.valdomiro.meusgastos.core.exceptions.RegistroInvalidoException
import com.santos.valdomiro.meusgastos.features.categoria.data.dao.CategoriaDao
import com.santos.valdomiro.meusgastos.features.categoria.data.model.CategoriaLocalModel
import javax.inject.Inject

class CategoriaLocalDataSourceImpl @Inject constructor(
    private val dao: CategoriaDao
) : CategoriaLocalDataSource {

    override suspend fun insertCategoria(catetoria: CategoriaLocalModel) {
        mapearExceptions { dao.insert(catetoria) }
    }

    override suspend fun updateCategoria(catetoria: CategoriaLocalModel) {
        mapearExceptions { dao.update(catetoria) }
    }

    override suspend fun deleteCategoria(catetoria: CategoriaLocalModel) {
        mapearExceptions { dao.delete(catetoria) }
    }

    override suspend fun getOnById(categoriaId: String): CategoriaLocalModel? {
        return mapearExceptions { dao.getOneById(categoriaId) }
    }

    override suspend fun getAll(): List<CategoriaLocalModel> {
        return mapearExceptions { dao.getAll() }
    }

    private suspend fun <T> mapearExceptions(action: suspend () -> T): T {
        return try {
            action()
        } catch (e: Exception) {
            throw mapearException(e)
        }
    }

    private fun mapearException(e: Throwable): Exception {
        return when (e) {
            is SQLiteConstraintException -> RegistroDuplicadoException(e)
            is IllegalStateException -> RegistroInvalidoException(e)
            is SQLiteException -> ErroBancoDadosDesconhecidoException(e)
            is Exception -> e
            else -> ErroBancoDadosDesconhecidoException(e)
        }
    }
}