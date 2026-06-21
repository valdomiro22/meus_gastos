package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.listacategorias

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.features.categoria.presentation.components.ItemCategoriaComponent
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.navigation.Route
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors
import com.santos.valdomiro.meusgastos.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaCategoriasScreen(
    viewModel: ListaCategoriasViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Categorias",
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
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
                onClick = { navController.navigate(Route.AdicionarCategoriaRoute.route) },
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
                val listaCategorias =
                    (state as? UiState.Success<List<CategoriaEntity>>)?.data ?: emptyList()

                if (listaCategorias.isEmpty()) {
                    EmptyListScreen(mensagem = "Toque no botão + para adicionar uma categoria e organizar seus gastos.")
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
                            items = listaCategorias,
                            key = { categoria -> categoria.id }
                        ) { categoria ->
                            ItemCategoriaComponent(
                                nome = categoria.nome,
                                data = categoria.criadoEm,
                                onEditar = {
                                    navController.navigate(
                                        Route.EditarCategoriaRoute.route
                                    )
                                },
                                onDeletar = { viewModel.deltarCategoria(categoria) },
                            )
                        }
                    }
                }
            }
        }
    }
}