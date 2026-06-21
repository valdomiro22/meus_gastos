package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.totalporcategoria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ItemTotalPorCategoriaComponent(
    nomeCategoria: String,
    totalGasto: Double,
    quantidadeGastos: Int? = null,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()

    val valorFormatado = NumberFormat
        .getCurrencyInstance(Locale("pt", "BR"))
        .format(totalGasto)

    val cardColor = if (isDark) Color(0xFF101B2B) else Color(0xFFFFFFFF)
    val borderColor = if (isDark) Color(0xFF22324A) else Color(0xFFD7E3F5)

    val tituloColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A)
    val textoSecundarioColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)

    val destaqueColor = if (isDark) Color(0xFF60A5FA) else Color(0xFF2563EB)
    val destaqueContainerColor = if (isDark) Color(0xFF122B4A) else Color(0xFFE0ECFF)

    val totalContainerColor = if (isDark) Color(0xFF173B5C) else Color(0xFFE0F2FE)
    val totalTextColor = if (isDark) Color(0xFF7DD3FC) else Color(0xFF0369A1)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = tituloColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDark) 0.dp else 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(62.dp)
                    .background(
                        color = destaqueColor,
                        shape = RoundedCornerShape(50.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(destaqueContainerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = null,
                    tint = destaqueColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = nomeCategoria,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = tituloColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = if (quantidadeGastos != null) {
                        "$quantidadeGastos gastos registrados"
                    } else {
                        "Total gasto nesta categoria"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = textoSecundarioColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.widthIn(min = 110.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = totalContainerColor,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Payments,
                            contentDescription = null,
                            tint = totalTextColor,
                            modifier = Modifier.size(15.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Total",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = totalTextColor
                        )
                    }
                }

                Text(
                    text = valorFormatado,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = tituloColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemTotalPorCategoriaComponent() {
    ItemTotalPorCategoriaComponent(
        nomeCategoria = "Alimentação",
        totalGasto = 923.92,
        quantidadeGastos = 14
    )
}