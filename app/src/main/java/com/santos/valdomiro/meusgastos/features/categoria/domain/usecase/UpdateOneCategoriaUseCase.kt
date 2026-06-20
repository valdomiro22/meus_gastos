package com.santos.valdomiro.meusgastos.features.categoria.domain.usecase

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import java.time.Instant
import javax.inject.Inject

class UpdateOneCategoriaUseCase @Inject constructor(
    private val repository: CategoriaRepository
) {

    suspend operator fun invoke(nome: String, id: String, criadoEm: Instant): Result<Unit> {
        if (nome.isBlank()) {
            return Result.failure(IllegalArgumentException("O nome não pode estar vazio"))
        }

        if (id.isBlank()) {
            return Result.failure(IllegalArgumentException("ID não pode estar vazio"))
        }

        val editadoEm = Instant.now()

        val categoria = CategoriaEntity(
            id = id,
            nome = nome,
            criadoEm = criadoEm,
            editadoEm = editadoEm
        )

        return repository.updateCategoria(categoria)
    }

}