package com.santos.valdomiro.meusgastos.main

import androidx.compose.runtime.Composable
import com.santos.valdomiro.meusgastos.navigation.Route

@Composable
fun AppRootScreen(
) {

    MainAppScreen(
        startDestination = Route.ListaGastosRoute.route
    )
}