package com.santos.valdomiro.meusgastos.features.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.meusgastos.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.navigation.Route
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
//    val state by viewModel.uiState.collectAsState()
    var menuExpandido by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Gastos",
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = { menuExpandido = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Mais opções",
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false },
                        shape = RoundedCornerShape(18.dp),
                        containerColor = if (isDark) Color(0xFF172033) else Color(0xFFFFFFFF),
                        tonalElevation = 6.dp,
                        shadowElevation = 8.dp,
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isDark) Color(0xFF2A3A55) else Color(0xFFE2E8F0)
                        )
                    ) {
                        DropdownMenuItem(
                            text = { Text("Histórico") },
                            onClick = {
//                                menuExpandido = false
//                                navController.navigate(
//                                    Route.ListaMovimentacaoRoute.criarRota(
//                                        producaoId = producaoId
//                                    )
//                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Categorias") },
                            onClick = {
                                menuExpandido = false
                                navController.navigate(Route.ListaCategoriasRoute.route)
                            }
                        )
                    }
                },
                windowInsets = WindowInsets(0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = AppTopBarColors.titleColor(),
                    navigationIconContentColor = AppTopBarColors.titleColor(),
                    actionIconContentColor = AppTopBarColors.titleColor()
                )
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { navController.navigate(Route.AdicionarGastoRoute.route) },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color(0xFFFFFFFF)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar Grade"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
        ) {
            Text("Home Screen")
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    navController.navigate(Route.GastosPorCategoria.route)
                },
                text = "Lista por categoria"
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    navController.navigate(Route.GastosPorPeriodo.route)
                },
                text = "Lista por periodo"
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    navController.navigate(Route.ResumoDoMes.route)
                },
                text = "Resumo do Mês"
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    navController.navigate(Route.ResumoDoDia.route)
                },
                text = "Resumo do Dia"
            )
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    navController.navigate(Route.TotalGastoPorCategoria.route)
                },
                text = "Total por Categoria"
            )
        }
    }
}