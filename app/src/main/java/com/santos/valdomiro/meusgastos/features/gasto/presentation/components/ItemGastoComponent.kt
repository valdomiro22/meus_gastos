package com.santos.valdomiro.meusgastos.features.gasto.presentation.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.meusgastos.core.helper.DataParaDatePicker.formatToBrazilian
import java.time.LocalDate

@Composable
fun ItemGastoComponent(
    titulo: String,
    valor: String,
    data: LocalDate,
    categoria: String,
    observacao: String? = null,
    onEditar: () -> Unit = {},
    onDeletar: () -> Unit = {}
) {
    var menuExpandido by remember { mutableStateOf(false) }

    val isDark = isSystemInDarkTheme()
    val dataFormatada = data.formatToBrazilian()

    val cardColor = if (isDark) Color(0xFF101B2B) else Color(0xFFFFFFFF)
    val borderColor = if (isDark) Color(0xFF22324A) else Color(0xFFD7E3F5)

    val tituloColor = if (isDark) Color(0xFFF8FAFC) else Color(0xFF0F172A)
    val textoSecundarioColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
    val gastoColor = if (isDark) Color(0xFF60A5FA) else Color(0xFF2563EB)


    val gastoContainerColor = if (isDark) Color(0xFF122B4A) else Color(0xFFE0ECFF)
    val deleteColor = if (isDark) Color(0xFFFF7A7E) else Color(0xFFE23A3A)
    val categoriaContainerColor = if (isDark) Color(0xFF173B5C) else Color(0xFFE0F2FE)
    val categoriaTextColor = if (isDark) Color(0xFF7DD3FC) else Color(0xFF0369A1)

    val menuIconColor = if (isDark) Color(0xFFCBD5E1) else Color(0xFF475569)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 2.dp),
        shape = RoundedCornerShape(16.dp),
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
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(92.dp)
                    .background(
                        color = gastoColor,
                        shape = RoundedCornerShape(50.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = categoriaContainerColor,
                        contentColor = categoriaTextColor
                    ) {
                        Text(
                            text = categoria.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = dataFormatada,
                        style = MaterialTheme.typography.bodySmall,
                        color = textoSecundarioColor,
                        maxLines = 1
                    )
                }

                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = tituloColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!observacao.isNullOrBlank()) {
                    Text(
                        text = observacao,
                        style = MaterialTheme.typography.bodySmall,
                        color = textoSecundarioColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.widthIn(min = 96.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = gastoContainerColor,
                    contentColor = gastoColor
                ) {
                    Text(
                        text = "- R$ $valor",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                        maxLines = 1
                    )
                }

                Box {
                    IconButton(
                        onClick = { menuExpandido = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Opções",
                            tint = menuIconColor
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Editar") },
                            onClick = {
                                menuExpandido = false
                                onEditar()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Deletar",
                                    color = deleteColor
                                )
                            },
                            onClick = {
                                menuExpandido = false
                                onDeletar()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = deleteColor
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}