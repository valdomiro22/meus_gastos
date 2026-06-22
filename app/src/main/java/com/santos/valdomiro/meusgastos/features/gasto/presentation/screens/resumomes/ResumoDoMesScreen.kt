package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.resumomes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumoDoMesScreen(
    viewModel: ResumoDoMesViewModel = hiltViewModel()
) {
    val resumoState by viewModel.resumoState.collectAsState()
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getAll(LocalDate.now())
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Resumo Mensal",
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
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
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardPrincipalResumo(
                total = resumoState.totalGasto.toString(),
                quantidadeGastos = resumoState.quantidadeGastos
            )

            Text(
                text = "Análise de Valores",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CardMetricaPreco(
                    titulo = "Maior Gasto",
                    valor = "R$ ${resumoState.maiorValorGasto}",
                    subtitulo = resumoState.categoriaDoMaiorValor,
                    isMaior = true,
                    modifier = Modifier.weight(1f)
                )
                CardMetricaPreco(
                    titulo = "Menor Gasto",
                    valor = "R$ ${resumoState.menorValorGasto}",
                    subtitulo = resumoState.categoriaDoMenorValor,
                    isMaior = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Comportamento de Consumo",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 8.dp)
            )

            CardCategoriaDestaque(
                categoria = resumoState.categoriaMaisUtilizada,
                vezes = resumoState.quantidadeDeVezesCategoriaMaisUtilizada,
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}