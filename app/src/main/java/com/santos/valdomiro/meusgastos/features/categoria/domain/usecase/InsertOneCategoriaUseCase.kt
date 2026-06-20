package com.santos.valdomiro.meusgastos.features.categoria.domain.usecase

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

class InsertOneCategoriaUseCase @Inject constructor(
    private val repository: CategoriaRepository
) {

    suspend operator fun invoke(nome: String): Result<Unit> {
        if (nome.isBlank()) {
            return Result.failure(IllegalArgumentException("O nome não pode estar vazio"))
        }

        val idGerado = UUID.randomUUID().toString()
        val criadoEm = Instant.now()

        val categoria = CategoriaEntity(
            id = idGerado,
            nome = nome,
            criadoEm = criadoEm
        )

        return repository.insertCategoria(categoria)
    }

}