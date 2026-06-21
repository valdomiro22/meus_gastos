package com.santos.valdomiro.meusgastos.common.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ErroComponent(
    mensagem: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = mensagem,
        modifier = modifier,
        color = MaterialTheme.colorScheme.error,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )
}