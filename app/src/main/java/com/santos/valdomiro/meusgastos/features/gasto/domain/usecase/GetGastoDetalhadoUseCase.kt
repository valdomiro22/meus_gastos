package com.santos.valdomiro.meusgastos.features.gasto.domain.usecase

import com.santos.valdomiro.meusgastos.features.categoria.domain.repository.CategoriaRepository
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoDetalhado
import com.santos.valdomiro.meusgastos.features.gasto.domain.repository.GastoRepository
import javax.inject.Inject

class GetGastoDetalhadoUseCase @Inject constructor(
    private val gastoRepository: GastoRepository,
    private val categoriaRepository: CategoriaRepository
) {

    suspend operator fun invoke(): Result<List<GastoDetalhado>> {
        val gastosResult = gastoRepository.getAll()
        if (gastosResult.isFailure) {
            return Result.failure(
                gastosResult.exceptionOrNull()
                    ?: IllegalArgumentException("Erro ao buscar gastos")
            )
        }

        val gastos = gastosResult.getOrDefault(emptyList())
        if (gastos.isEmpty()) return Result.success(emptyList())

        val categoriaResult = categoriaRepository.getAll()
        if (categoriaResult.isFailure) {
            return Result.failure(
                categoriaResult.exceptionOrNull()
                    ?: java.lang.IllegalArgumentException("Erro ao buscar categorias")
            )
        }

        val categorias = categoriaResult.getOrDefault(emptyList())
        if (categorias.isEmpty()) return Result.success(emptyList())

        val categoriasPorId = categorias.associateBy { categoria -> categoria.id }
        val listaGastosDetalhados = gastos.map { gasto ->
            val categoria = categoriasPorId[gasto.categoriaId]

            GastoDetalhado(
                gastoId = gasto.id,
                gastoDescricao = gasto.descricao,
                gastoValor = gasto.valor,
                categoriaId = gasto.categoriaId,
                categoriaNome = categoria?.nome ?: "Sem categoria",
                gastoData = gasto.data,
                gastoObservacao = gasto.observacao,
                gastoCriadoEm = gasto.criadoEm,
            )
        }

        return Result.success(listaGastosDetalhados)
    }

}