package com.santos.valdomiro.meusgastos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.santos.valdomiro.meusgastos.HomeScreen
import com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.adicionargasto.AdicionarGastoScreen
import com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.listagastos.ListaGastosScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {

            // Home
            composable(Route.HomeRoute.route) {
                HomeScreen()
            }

            // Gasto
            composable(Route.ListaGastosRoute.route) {
                ListaGastosScreen()
            }
            composable(Route.AdicionarGastoRoute.route) {
                AdicionarGastoScreen()
            }
//            composable(
//                route = "editar-barril/{barrilId}",
//                arguments = listOf(navArgument("barrilId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val barrilId = backStackEntry.arguments?.getString("barrilId") ?: return@composable
//                EditarGastoScreen(barrilId = barrilId)
//            }

//            // Produto
//            composable(Route.ListaProdutosRoute.route) {
//                ListaProdutosScreen()
//            }
//            composable(Route.AdicionarProdutoRoute.route) {
//                AdicionarProdutoScreen()
//            }
//            composable(
//                route = "editar-produto/{produtoId}",
//                arguments = listOf(navArgument("produtoId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val produtoId =
//                    backStackEntry.arguments?.getString("produtoId") ?: return@composable
//                EditarProdutoScreen(produtoId = produtoId)
//            }
//
//            // Grade
//            composable(Route.ListaGradesRoute.route) {
//                ListaGradesScreen(
//                    onOpenDrawer = onOpenDrawer
//                )
//            }
//            composable(Route.AdicionarGradeRoute.route) {
//                AdicionarGradeScreen()
//            }
//            composable(
//                route = "editar-grade/{gradeId}",
//                arguments = listOf(navArgument("gradeId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
//                EditarGradeScreen(gradeId = gradeId)
//            }
//
//            // Produção
//            composable(
//                route = "lista-producoes/{gradeId}",
//                arguments = listOf(navArgument("gradeId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
//                ListaProducaoScreen(
//                    gradeId = gradeId,
//                    onOpenDrawer = onOpenDrawer
//                )
//            }
//            composable(
//                route = "adicionar-producao/{gradeId}",
//                arguments = listOf(navArgument("gradeId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val gradeId = backStackEntry.arguments?.getString("gradeId") ?: return@composable
//                AdicionarProducaoScreen(gradeId = gradeId)
//            }
//            composable(
//                route = "editar-producao/{producaoId}",
//                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val producaoId =
//                    backStackEntry.arguments?.getString("producaoId") ?: return@composable
//                EditarProducaoScreen(producaoId = producaoId)
//            }
//
//            // Movimentação
//            composable(
//                route = "lista-movimentacao/{producaoId}",
//                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val producaoId =
//                    backStackEntry.arguments?.getString("producaoId") ?: return@composable
//                ListaMovimentacaoScreen(producaoId = producaoId)
//            }
//
//            // Calcular tempo de parada
//            composable(Route.CalcularTempoDeParadaRoute.route) {
//                CalcularTempoParadaScreen(
//                    onOpenDrawer = onOpenDrawer,
//                )
//            }
//
//            // Verificar validade
//            composable(Route.VerificarValidadeRoute.route) {
//                VerificarValidadeScreen(
//                    onOpenDrawer = onOpenDrawer,
//                )
//            }
//
//            // Simular fim de Produção
//            composable(
//                route = "simular-fim-producao/{producaoId}",
//                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val producaoId =
//                    backStackEntry.arguments?.getString("producaoId") ?: return@composable
//                SimularFimProducaoScreen(producaoId = producaoId)
//            }
//
//            // Status da Produção
//            composable(
//                route = "status-producao/{producaoId}",
//                arguments = listOf(navArgument("producaoId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val producaoId =
//                    backStackEntry.arguments?.getString("producaoId") ?: return@composable
//                StatusDaProducaoScreen(producaoId = producaoId)
//            }

        }
    }

}