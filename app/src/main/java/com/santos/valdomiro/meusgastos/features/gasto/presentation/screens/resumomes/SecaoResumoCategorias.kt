package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.resumomes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecaoResumoCategorias(
    categoriaMaisUtilizada: String,
    quantidadeCategoriaMaisUtilizada: Int,
    categoriaDoMaiorValor: String,
    categoriaDoMenorValor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.run {
            cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        },
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Categorias",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            ItemResumoCategoria(
                titulo = "Categoria mais utilizada",
                valor = if (categoriaMaisUtilizada.isBlank()) {
                    "-"
                } else {
                    "$categoriaMaisUtilizada ($quantidadeCategoriaMaisUtilizada vezes)"
                }
            )

            ItemResumoCategoria(
                titulo = "Categoria do maior gasto",
                valor = categoriaDoMaiorValor.ifBlank { "-" }
            )

            ItemResumoCategoria(
                titulo = "Categoria do menor gasto",
                valor = categoriaDoMenorValor.ifBlank { "-" }
            )
        }
    }
}