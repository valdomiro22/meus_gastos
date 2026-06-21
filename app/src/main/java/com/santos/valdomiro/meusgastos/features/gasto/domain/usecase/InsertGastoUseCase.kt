package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class InsertGastoUseCase @Inject constructor(
    private val repository: GastoRepository
) {

    suspend operator fun invoke(params: InsertGastoParams): Result<Unit> {
        if (params.descricao.isBlank()) {
            return Result.failure(IllegalArgumentException("A descrição não pode estar vazia"))
        }

        if (params.valor <= 0.0) {
            return Result.failure(IllegalArgumentException("O valor deve ser maior do que zero"))
        }

        if (params.categoriaId.isBlank()) {
            return Result.failure(IllegalArgumentException("ID da categoria não pode esta vazio"))
        }

        if (params.categoriaNome.isEmpty()) {
            return Result.failure(IllegalArgumentException("Nome da categoria não pode esta vazio"))
        }

        val idGerado = UUID.randomUUID().toString()
        val criadoEm = Instant.now()

        val gasto = GastoEntity(
            id = idGerado,
            descricao = params.descricao,
            valor = params.valor,
            categoriaId = params.categoriaId,
            categoriaNome = params.categoriaNome,
            data = params.data,
            observacao = params.observacao,
            criadoEm = criadoEm,
        )

        return repository.insertGasto(gasto)
    }

}

data class InsertGastoParams(
    val descricao: String,
    val valor: Double,
    val categoriaId: String,
    val categoriaNome: String,
    val data: LocalDate,
    val observacao: String?,
)