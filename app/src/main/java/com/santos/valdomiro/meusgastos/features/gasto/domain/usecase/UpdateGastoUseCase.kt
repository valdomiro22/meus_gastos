package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

class UpdateGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(params: UpdateGastoParams): Result<Unit> {
        if (params.descricao.isBlank()) {
            return Result.failure(IllegalArgumentException("A descrição não pode estar vazia"))
        }

        if (params.valor <= 0.0) {
            return Result.failure(IllegalArgumentException("O valor deve ser maior do que zero"))
        }

        val editadoEm = Instant.now()

        val gasto = GastoEntity(
            id = params.id,
            descricao = params.descricao,
            valor = params.valor,
            categoriaId = params.categoriaId,
            data = params.data,
            observacao = params.observacao,
            criadoEm = params.criadoEm,
            editadoEm = editadoEm
        )

        return repository.updateGasto(gasto)
    }

}

data class UpdateGastoParams(
    val id: String,
    val descricao: String,
    val valor: Double,
    val categoriaId: String?,
    val data: LocalDate,
    val observacao: String?,
    val criadoEm: Instant
)