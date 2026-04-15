package com.zillotrix.moneytracker.features.expenses.presentation.ui.common

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.zillotrix.moneytracker.core.utils.getMonthRange
import java.time.YearMonth
import java.util.Date

@Composable
fun ExpenseDatePickerDialog(
    selectedDate: Date?,
    yearMonth: YearMonth,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val (startOfMonth, endOfMonth) = yearMonth.getMonthRange()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.time?.coerceIn(startOfMonth, endOfMonth),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in startOfMonth..endOfMonth
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}