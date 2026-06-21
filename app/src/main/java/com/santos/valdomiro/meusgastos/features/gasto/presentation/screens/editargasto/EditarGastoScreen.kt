package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.editargasto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.meusgastos.common.components.CarregandoComponent
import com.santos.valdomiro.meusgastos.common.components.CustomOutlinedTextField
import com.santos.valdomiro.meusgastos.common.components.DatePickerDialogComponent
import com.santos.valdomiro.meusgastos.common.components.ErroComponent
import com.santos.valdomiro.meusgastos.common.state.UiState
import com.santos.valdomiro.meusgastos.features.categoria.presentation.components.DropdownCategoria
import com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.listacategorias.ListaCategoriasViewModel
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarGastoScreen(
    gastoId: String,
    gastoViewModel: EditarGastoViewModel = hiltViewModel(),
    categoriaVM: ListaCategoriasViewModel = hiltViewModel()
) {

    val gastoState by gastoViewModel.uiState.collectAsState()
    val categoriaState by categoriaVM.uiState.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        gastoViewModel.buscarGasto(gastoId = gastoId)
        categoriaVM.getAll()
    }

    LaunchedEffect(gastoState.isEditSuccess) {
        if (gastoState.isEditSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar Gasto",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
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
                .padding(innerPadding)
                .padding(start = 12.dp, end = 12.dp, bottom = 24.dp)
                .fillMaxWidth()
        ) {
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = gastoState.descricao,
                onValueChange = gastoViewModel::onDescricaoChanged,
                label = "Descrição",
                isErro = gastoState.erroDescricao != null,
            )
            if (gastoState.erroDescricao != null) ErroComponent(gastoState.erroDescricao!!)
            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = gastoState.valor,
                onValueChange = gastoViewModel::onValorChanged,
                placeholder = "Ex: 9.59",
                label = "Valor",
                isErro = gastoState.erroValor != null,
            )
            if (gastoState.erroValor != null) ErroComponent(gastoState.erroValor!!)
            Spacer(modifier = Modifier.height(8.dp))

            when (val categoriaWhenState = categoriaState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Carregando barris...",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is UiState.Success -> {
                    DropdownCategoria(
                        listaEntity = categoriaWhenState.data,
                        itemIdAtual = gastoState.categoriaId,
                        onItemSelecionado = gastoViewModel::onCategoriaChanged,
                        navController = navController
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = "Erro ao carregar barris: ${categoriaWhenState.message}",
                        color = Color.Red
                    )
                }

                is UiState.Idle -> {
                    Text("Aguardando carregamento dos barris...")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            DatePickerDialogComponent(
                selectedDate = gastoState.data,
                onDateSelected = gastoViewModel::onDataChanged,
                label = "Data de Produção"
            )
            if (gastoState.erroData != null) ErroComponent(gastoState.erroData!!)
            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = gastoState.observacao ?: "",
                onValueChange = gastoViewModel::onObservacaoChanged,
                placeholder = "Observação...",
                label = "Observação",
                isErro = gastoState.erroObservacao != null,
                minLines = 2,
                maxLines = 5
            )
            if (gastoState.erroObservacao != null) ErroComponent(gastoState.erroObservacao!!)
            if (gastoState.erroGeral != null) ErroComponent(gastoState.erroGeral!!)
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    gastoViewModel.editarGasto()
                },
                text = "Salvar"
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (gastoState.isLoading) CarregandoComponent()

        }
    }
}
