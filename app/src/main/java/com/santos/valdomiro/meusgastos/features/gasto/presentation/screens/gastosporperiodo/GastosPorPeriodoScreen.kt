package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.gastosporperiodo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.meusgastos.common.components.DatePickerDialogMenorComponent
import com.santos.valdomiro.meusgastos.common.components.EmptyListScreen
import com.santos.valdomiro.meusgastos.common.components.ErroComponent
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.gasto.domain.entity.GastoEntity
import com.santos.valdomiro.meusgastos.features.gasto.presentation.components.ItemGastoComponent
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.navigation.Route
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosPorPeriodoScreen(
    viewModel: GastosPorPeriodoViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val state by viewModel.uiState.collectAsState()
    val isDark = isSystemInDarkTheme()
    val inicioSelecionado by viewModel.dataInicioSelecionada.collectAsState()
    val fimSelecionado by viewModel.dataFimSelecionada.collectAsState()
    val erroInicio by viewModel.erroInicio.collectAsState()
    val erroFim by viewModel.erroFim.collectAsState()

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
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DatePickerDialogMenorComponent(
                    modifier = Modifier.weight(1f),
                    selectedDate = inicioSelecionado,
                    onDateSelected = viewModel::onInicioChanged,
                    label = "Início"
                )
                Spacer(modifier = Modifier.width(12.dp))

                DatePickerDialogMenorComponent(
                    modifier = Modifier.weight(1f),
                    selectedDate = fimSelecionado,
                    onDateSelected = viewModel::onFimChanged,
                    label = "Fim",
                )

            }
            if (erroInicio != null) ErroComponent(erroInicio!!)
            if (erroFim != null) ErroComponent(erroFim!!)
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = { viewModel.buscarGastos() },
                text = "Filtrar"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Gastos
            when {
                state is UiState.Idle -> {
                    EmptyListScreen(
                        mensagem = "Selecione um período para visualizar os gastos."
                    )
                }

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
                        modifier = Modifier.fillMaxWidth(),
                        mensagem = (state as? UiState.Error)?.message
                            ?: "Erro desconhecido ao listar gastos"
                    )
                }

                state.isSuccess -> {
                    val listaGastos =
                        (state as? UiState.Success<List<GastoEntity>>)?.data ?: emptyList()

                    if (listaGastos.isEmpty()) {
                        EmptyListScreen(
                            mensagem = "Nenhum gasto encontrado para este período."
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
                                    onDeletar = { viewModel.deletarGasto(gasto) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}