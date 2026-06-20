package com.santos.valdomiro.meusgastos.features.categoria.domain.usecase

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import javax.inject.Inject

class DeleteOneCategoriaUseCase @Inject constructor(
    private val repository: CategoriaRepository
) {

    suspend operator fun invoke(categoria: CategoriaEntity): Result<Unit> {
        if (categoria.id.isBlank()) {
            return Result.failure(IllegalArgumentException("ID da categoria não pode estar vazio"))
        }

        return repository.deleteCategoria(categoria)
    }

}