package com.santos.valdomiro.meusgastos.features.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SecaoAtalhos(
    onGastosPorCategoriaClick: () -> Unit,
    onGastosPorPeriodoClick: () -> Unit,
    onResumoDoMesClick: () -> Unit,
    onResumoDoDiaClick: () -> Unit,
    onTotalPorCategoriaClick: () -> Unit
) {
    Column {
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
            onClick = onGastosPorCategoriaClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        AtalhoHomeCard(
            titulo = "Gastos por período",
            descricao = "Filtre gastos entre duas datas",
            marcador = "P",
            onClick = onGastosPorPeriodoClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        AtalhoHomeCard(
            titulo = "Resumo do mês",
            descricao = "Veja o total gasto no mês selecionado",
            marcador = "M",
            onClick = onResumoDoMesClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        AtalhoHomeCard(
            titulo = "Resumo do dia",
            descricao = "Veja os gastos de uma data específica",
            marcador = "D",
            onClick = onResumoDoDiaClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        AtalhoHomeCard(
            titulo = "Total por categoria",
            descricao = "Confira quanto foi gasto em cada categoria",
            marcador = "T",
            onClick = onTotalPorCategoriaClick
        )
    }
}