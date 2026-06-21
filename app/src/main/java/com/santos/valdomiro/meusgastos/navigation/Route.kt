package com.santos.valdomiro.meusgastos.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val title: String = "",
    val icon: ImageVector? = null
) {
    data object HomeRoute : Route(route = "home", title = "Home")

    // gasto
    data object ListaGastosRoute : Route(route = "lista-gastos", title = "Lista de Gastos")
    data object AdicionarGastoRoute : Route(route = "adicionar-gasto", title = "Adicionar Gasto")
    data object EditarGastoRoute : Route(route = "editar-gasto/{gastoId}", title = "Editar Gasto") {
        fun criarRota(gastoId: String) = "editar-gasto/$gastoId"
    }
    data object GastosPorCategoria : Route(route = "gastos-por-categoria", title = "Gastos")
    data object GastosPorPeriodo : Route(route = "gastos-por-periodo", title = "Gastos")
    data object ResumoDoMes : Route(route = "resumo-do-mes", title = "Resumo do Mês")

    // Categoria
    data object AdicionarCategoriaRoute :
        Route(route = "adicionar-categoria", title = "Adicionar Categoria")

    data object ListaCategoriasRoute :
        Route(route = "lista-categorias", title = "Lista de Categorias")

    data object EditarCategoriaRoute :
        Route(route = "editar-categoria", title = "Editar Categoria")

}
