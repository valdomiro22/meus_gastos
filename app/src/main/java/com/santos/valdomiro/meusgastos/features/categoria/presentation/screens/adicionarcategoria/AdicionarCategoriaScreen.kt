package com.santos.valdomiro.meusgastos.features.categoria.presentation.screens.adicionarcategoria

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.santos.valdomiro.meusgastos.common.components.ButtomFillMaxWidth
import com.santos.valdomiro.meusgastos.common.components.CustomOutlinedTextField
import com.santos.valdomiro.meusgastos.common.components.ErroComponent
import com.santos.valdomiro.meusgastos.navigation.LocalNavController
import com.santos.valdomiro.meusgastos.ui.theme.AppTopBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarCategoriaScreen(
    viewModel: AdicionarCategoriaViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
            Toast.makeText(context, "Grade salva", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar Categoria",
                        fontWeight = FontWeight.W500,
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
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = 0.dp
                )
                .padding(start = 12.dp, end = 12.dp, bottom = 24.dp)
                .fillMaxWidth()
        ) {
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.nome,
                inputType = KeyboardType.Text,
                onValueChange = viewModel::onNomeChanged,
                label = "Nome",
                isErro = state.erroNome != null,
                isPrimeiraLetraMaiuscula = true
            )
            if (state.erroNome != null) ErroComponent(state.erroNome!!)
            Spacer(modifier = Modifier.height(16.dp))

            ButtomFillMaxWidth(
                onClick = { viewModel.adicionarCategoria() },
                text = "Adicionar"
            )
        }
    }
}