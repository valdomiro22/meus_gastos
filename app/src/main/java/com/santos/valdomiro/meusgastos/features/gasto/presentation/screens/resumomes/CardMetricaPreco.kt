package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.resumomes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CardMetricaPreco(
    titulo: String,
    valor: String,
    subtitulo: String,
    isMaior: Boolean,
    modifier: Modifier = Modifier
) {
    val corBadge = if (isMaior) Color(0xFFFCE8E6) else Color(0xFFE6F4EA)
    val corTextoBadge = if (isMaior) Color(0xFFC5221F) else Color(0xFF137333)

    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .background(corBadge, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(titulo, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = corTextoBadge)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                valor,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitulo.ifEmpty { "Sem categoria" },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardMetricaPreco() {
    CardMetricaPreco("Titulo", "2938", "Subtitulo", true)
}