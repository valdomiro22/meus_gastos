package com.santos.valdomiro.meusgastos.features.categoria.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.santos.valdomiro.meusgastos.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.meusgastos.features.categoria.domain.entity.CategoriaEntity
import com.santos.valdomiro.meusgastos.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCategoria(
    listaEntity: List<CategoriaEntity>,
    itemIdAtual: String?,
    onItemSelecionado: (CategoriaEntity) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }

    var textoBusca by remember(itemIdAtual, listaEntity) {
        mutableStateOf(
            listaEntity.firstOrNull { it.id == itemIdAtual }?.nome ?: ""
        )
    }

    val nomeCategoriaAtual = remember(itemIdAtual, listaEntity) {
        listaEntity.firstOrNull { it.id == itemIdAtual }?.nome ?: ""
    }

    // 2. Atualizamos a lógica do filtro
    val listaFiltrada by remember(textoBusca, listaEntity, nomeCategoriaAtual) {
        derivedStateOf {
            // Se o campo estiver vazio OU se o texto for exatamente o produto já selecionado,
            // nós mostramos a lista inteira novamente.
            if (textoBusca.isBlank() || textoBusca == nomeCategoriaAtual) {
                listaEntity
            } else {
                // Caso contrário, o usuário está realmente digitando para buscar algo novo
                listaEntity.filter {
                    it.nome.contains(textoBusca, ignoreCase = true)
                }
            }
        }
    }

    if (listaFiltrada.isEmpty()) {
        ButtomFillMaxWidth(
            onClick = { navController.navigate(Route.AdicionarCategoriaRoute.route) },
            text = "Adicionar Categoria"
        )
    } else {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = textoBusca,
                onValueChange = {
                    textoBusca = it
                    expanded = true
                },
                label = { Text("Categoria") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listaFiltrada.forEach { produto ->
                    DropdownMenuItem(
                        text = { Text("Categoria de ${produto.nome}") },
                        onClick = {
                            onItemSelecionado(produto)
                            textoBusca = produto.nome
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
