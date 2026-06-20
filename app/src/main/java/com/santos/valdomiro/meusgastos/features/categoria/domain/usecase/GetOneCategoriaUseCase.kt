package com.santos.valdomiro.meusgastos.features.categoria.domain.usecase

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import javax.inject.Inject

class GetOneCategoriaUseCase @Inject constructor(
    private val repository: CategoriaRepository
) {

    suspend operator fun invoke(categoriaId: String): Result<CategoriaEntity> {
        val result = repository.getOnById(categoriaId)

        return result.fold(
            onSuccess = { categoria ->
                if (categoria == null) {
                    return Result.failure(IllegalArgumentException("Categoria não encontrado com o ID: $categoria"))
                }

                Result.success(categoria)
            },
            onFailure = { exception ->
                Result.failure(
                    IllegalArgumentException("Erro ao buscar categoria com o ID: $categoriaId, $exception")
                )
            }
        )
    }

}