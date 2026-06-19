package com.santos.valdomiro.meusgastos.features.gasto.data.localdatasource

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.santos.valdomiro.meusgastos.core.exceptions.ErroBancoDadosDesconhecidoException
import com.santos.valdomiro.meusgastos.core.exceptions.RegistroDuplicadoException
import com.santos.valdomiro.meusgastos.core.exceptions.RegistroInvalidoException
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.features.gasto.data.dao.GastoDao
import com.santos.valdomiro.meusgastos.features.gasto.data.model.GastoLocalModel
import javax.inject.Inject

class GastoLocalDataSourceImpl @Inject constructor(
    private val dao: GastoDao
) : GastoLocalDataSource {

    override suspend fun insertGasto(gasto: GastoLocalModel) {
        mapearExceptions { dao.insert(gasto) }
    }

    override suspend fun updateGasto(gasto: GastoLocalModel) {
        mapearExceptions { dao.update(gasto) }
    }

    override suspend fun deleteGasto(gasto: GastoLocalModel) {
        mapearExceptions { dao.delete(gasto) }
    }

    override suspend fun getOnById(gastoId: String): GastoLocalModel? {
        return mapearExceptions { dao.getOneById(id = gastoId) }
    }

    override suspend fun getAll(): List<GastoLocalModel> {
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