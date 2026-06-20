package com.santos.valdomiro.meusgastos.features.categoria.domain.usecase

import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import javax.inject.Inject

class GetAllCategoriasUseCase @Inject constructor(
    private val repository: CategoriaRepository
) {

    suspend operator fun invoke(): Result<List<CategoriaEntity>> {
        return repository.getAll()
    }

}