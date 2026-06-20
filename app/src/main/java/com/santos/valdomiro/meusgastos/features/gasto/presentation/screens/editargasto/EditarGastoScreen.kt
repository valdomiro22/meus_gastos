package com.santos.valdomiro.meusgastos.features.gasto.presentation.screens.editargasto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.meusgastos.common.components.CarregandoComponent
import com.santos.valdomiro.meusgastos.common.components.CustomOutlinedTextField
import com.santos.valdomiro.meusgastos.common.components.DatePickerDialogComponent
import com.santos.valdomiro.meusgastos.common.components.ErroComponent
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarGastoScreen(
    gastoId: String,
    viewModel: EditarGastoViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.buscarGasto(gastoId = gastoId)
    }

    LaunchedEffect(state.isEditSuccess) {
        if (state.isEditSuccess) {
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
                value = state.descricao,
                onValueChange = viewModel::onDescricaoChanged,
                label = "Descrição",
                isErro = state.erroDescricao != null,
            )
            if (state.erroDescricao != null) ErroComponent(state.erroDescricao!!)
            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.valor,
                onValueChange = viewModel::onValorChanged,
                placeholder = "Ex: 9.59",
                label = "Valor",
                isErro = state.erroValor != null,
            )
            if (state.erroValor != null) ErroComponent(state.erroValor!!)
            Spacer(modifier = Modifier.height(8.dp))

            DatePickerDialogComponent(
                selectedDate = state.data,
                onDateSelected = viewModel::onDataChanged,
                label = "Data de Produção"
            )
            if (state.erroData != null) ErroComponent(state.erroData!!)
            Spacer(modifier = Modifier.height(8.dp))

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.observacao ?: "",
                onValueChange = viewModel::onObservacaoChanged,
                placeholder = "Observação...",
                label = "Observação",
                isErro = state.erroObservacao != null,
                minLines = 2,
                maxLines = 5
            )
            if (state.erroObservacao != null) ErroComponent(state.erroObservacao!!)
            if (state.erroGeral != null) ErroComponent(state.erroGeral!!)
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = {
                    viewModel.onCategoriaIdChanged("sdfds")
                    viewModel.editarGasto()
                },
                text = "Salvar"
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (state.isLoading) CarregandoComponent()

        }
    }
}
