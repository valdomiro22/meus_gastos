package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.gastosporcategoria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.santos.valdomiro.meusgastos.features.categoria.presentation.components.DropdownCategoria
import com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.listacategorias.ListaCategoriasViewModel
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.presentation.components.ItemGastoComponent
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.navigation.Route
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosPorCategoriaScreen(
    gastoVM: GastosPorCategoriaViewModel = hiltViewModel(),
    categoriaVM: ListaCategoriasViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val gastoState by gastoVM.uiState.collectAsState()
    val categoriaState by categoriaVM.uiState.collectAsState()
    var menuExpandido by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val categoriaSelecionadaId by gastoVM.categoriaSelecionadaId.collectAsState()

    LaunchedEffect(Unit) {
        categoriaVM.getAll()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Gastos da categoria",
                        fontWeight = FontWeight.W500,
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = 0.dp
                )
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            when (val categoriaWhenState = categoriaState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Carregando categorias...",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is UiState.Success -> {
                    DropdownCategoria(
                        listaEntity = categoriaWhenState.data,
                        itemIdAtual = categoriaSelecionadaId,
                        onItemSelecionado = gastoVM::onCategoriaChanged,
                        navController = navController
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = "Erro ao carregar categorias: ${categoriaWhenState.message}",
                        color = Color.Red
                    )
                }

                is UiState.Idle -> {
                    Text("Aguardando carregamento das categorias...")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))


            // Gastos
            when {
                gastoState is UiState.Idle -> {
                    EmptyListScreen(
                        mensagem = "Selecione uma categoria para visualizar os gastos."
                    )
                }

                gastoState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                gastoState.isError -> {
                    ErroComponent(
                        mensagem = (gastoState as? UiState.Error)?.message
                            ?: "Erro desconhecido ao listar gastos"
                    )
                }

                gastoState.isSuccess -> {
                    val listaGastos =
                        (gastoState as? UiState.Success<List<GastoEntity>>)?.data ?: emptyList()

                    if (listaGastos.isEmpty()) {
                        EmptyListScreen(
                            mensagem = "Nenhum gasto encontrado para esta categoria."
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(
                                items = listaGastos,
                                key = { gasto -> gasto.id }
                            ) { gasto ->
                                ItemGastoComponent(
                                    titulo = gasto.descricao,
                                    valor = gasto.valor.toString(),
                                    data = gasto.data,
                                    categoria = gasto.categoriaNome,
                                    observacao = gasto.observacao,
                                    onEditar = {
                                        navController.navigate(
                                            Route.EditarGastoRoute.criarRota(
                                                gastoId = gasto.id
                                            )
                                        )
                                    },
                                    onDeletar = { gastoVM.deletarGasto(gasto) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}