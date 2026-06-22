package com.santos.valdomiro.meusgastos.features.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.core.util.TAG
import com.santos.valdomiro.meusgastos.core.util.mesAtualFormatado
import com.santos.valdomiro.meusgastos.core.util.mesAtualFormatadoSemAno
import com.santos.valdomiro.meusgastos.features.home.components.AtalhoHomeCard
import com.santos.valdomiro.meusgastos.features.home.components.PequenoCardResumo
import com.santos.valdomiro.meusgastos.features.home.components.ResumoPrincipalCard
import com.santos.valdomiro.meusgastos.features.home.components.SecaoCategoriasComponent
import com.santos.valdomiro.meusgastos.features.home.components.UltimosGastosCard
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.navigation.Route
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    var menuExpandido by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val qtCategorias by viewModel.qtCategorias.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.carregarResumo(LocalDate.now())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meus Gastos",
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
                        containerColor = if (isDark) Color(0xFF172033) else Color.White,
                        tonalElevation = 6.dp,
                        shadowElevation = 8.dp,
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isDark) Color(0xFF2A3A55) else Color(0xFFE2E8F0)
                        )
                    ) {
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
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar gasto"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Resumo de ${mesAtualFormatadoSemAno()}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = mesAtualFormatado(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            ResumoPrincipalCard(
                totalGastoMes = state.totalGastoMes,
                isLoading = state.isLoading
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PequenoCardResumo(
                    modifier = Modifier.weight(1f),
                    titulo = "Gastos",
                    valor = state.quantidadeGastosMes.toString(),
                    descricao = "registros no mês"
                )

                PequenoCardResumo(
                    modifier = Modifier.weight(1f),
                    titulo = "Período",
                    valor = "Mês",
                    descricao = "resumo atual"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            UltimosGastosCard(
                gastos = state.ultimosGastos
            )

            Spacer(modifier = Modifier.height(20.dp))

            SecaoCategoriasComponent(quantidade = qtCategorias, navController = navController)

            Text(
                text = "Atalhos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            AtalhoHomeCard(
                titulo = "Gastos por categoria",
                descricao = "Veja seus gastos separados por categoria",
                marcador = "C",
                onClick = {
                    navController.navigate(Route.GastosPorCategoria.route)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AtalhoHomeCard(
                titulo = "Gastos por período",
                descricao = "Filtre gastos entre duas datas",
                marcador = "P",
                onClick = {
                    navController.navigate(Route.GastosPorPeriodo.route)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AtalhoHomeCard(
                titulo = "Resumo do mês",
                descricao = "Veja o total gasto no mês selecionado",
                marcador = "M",
                onClick = {
                    navController.navigate(Route.ResumoDoMes.route)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AtalhoHomeCard(
                titulo = "Resumo do dia",
                descricao = "Veja os gastos de uma data específica",
                marcador = "D",
                onClick = {
                    navController.navigate(Route.ResumoDoDia.route)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AtalhoHomeCard(
                titulo = "Total por categoria",
                descricao = "Confira quanto foi gasto em cada categoria",
                marcador = "T",
                onClick = {
                    navController.navigate(Route.TotalGastoPorCategoria.route)
                }
            )

            if (state.erro != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.erro!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}