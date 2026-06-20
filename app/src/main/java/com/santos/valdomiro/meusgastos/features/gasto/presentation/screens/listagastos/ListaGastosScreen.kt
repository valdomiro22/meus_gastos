package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.listagastos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.common.components.EmptyListScreen
import com.santos.valdomiro.meusgastos.common.components.ErroComponent
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoDetalhado
import com.santos.valdomiro.meusgastos.features.gasto.presentation.components.ItemGastoComponent
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.navigation.Route
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors
import com.santos.valdomiro.meusgastos.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaGastosScreen(
    viewModel: ListaGastosDetalhadosViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val state by viewModel.uiState.collectAsState()
    var menuExpandido by remember { mutableStateOf(false) }  // Para o controle do DropdownMenu
    val isDark = isSystemInDarkTheme()

    LaunchedEffect(Unit) {
        viewModel.getAll()
    }


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
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.isError -> {
                ErroComponent(
                    mensagem = (state as? UiState.Error)?.message
                        ?: "Erro desconhecido ao listar contadores"
                )
            }

            state.isSuccess -> {
                val listaGastos =
                    (state as? UiState.Success<List<GastoDetalhado>>)?.data ?: emptyList()

                if (listaGastos.isEmpty()) {
                    EmptyListScreen(mensagem = "Toque no botão + para adicionar um gasto e listar seus gastos.")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = innerPadding.calculateTopPadding(),
                                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                                bottom = 0.dp
                            )
                            .padding(horizontal = Dimens.paddingHorizontal),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(
                            items = listaGastos,
                            key = { gasto -> gasto.gastoId }
                        ) { gasto ->
                            ItemGastoComponent(
                                titulo = gasto.gastoDescricao,
                                valor = gasto.gastoValor.toString(),
                                data = gasto.gastoData,
                                categoria = gasto.categoriaNome,
                                observacao = gasto.gastoObservacao,
                                onEditar = {
                                    navController.navigate(
                                        Route.EditarGastoRoute.criarRota(
                                            gastoId = gasto.gastoId
                                        )
                                    )
                                },
                                onDeletar = { viewModel.deletarGasto(gasto) },
                            )
                        }
                    }
                }
            }
        }
    }
}