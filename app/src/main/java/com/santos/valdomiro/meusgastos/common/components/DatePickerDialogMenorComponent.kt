package com.santos.valdomiro.meusgastos.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santos.valdomiro.meusgastos.core.helper.DataParaDatePicker.formatToBrazilian
import com.santos.valdomiro.meusgastos.core.helper.DataParaDatePicker.toEpochMillis
import com.santos.valdomiro.meusgastos.core.helper.DataParaDatePicker.toLocalDate
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogMenorComponent(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate?) -> Unit,
    label: String = "Data",
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochMillis()
    )

    var showDatePicker by remember { mutableStateOf(false) }

    val formattedDate = selectedDate.formatToBrazilian()

        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            shape = RoundedCornerShape(10.dp),
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Selecionar data"
                    )
                }
            },
            modifier = modifier
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val newDate = datePickerState.selectedDateMillis.toLocalDate()
                            onDateSelected(newDate)
                            showDatePicker = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = true
                )
            }
        }
}