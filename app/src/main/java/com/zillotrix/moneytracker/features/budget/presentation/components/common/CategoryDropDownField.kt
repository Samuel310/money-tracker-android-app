package com.zillotrix.moneytracker.features.budget.presentation.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownField() {

    val categories = listOf(
        "Food", "Rent", "Travel", "Entertainment", "Petrol", "Electricity"
    )

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }

    val filteredCategories = remember(text.text) {
        if (text.text.isBlank()) categories
        else categories.filter {
            it.contains(text.text, ignoreCase = true)
        }
    }

    Box {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    expanded = focusState.isFocused
                },
            value = text,
            onValueChange = {
                text = it
                expanded = true
            },
            label = { Text("Category") }
        )

        DropdownMenu(
            expanded = expanded && filteredCategories.isNotEmpty(),
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false),
            modifier = Modifier.fillMaxWidth()
        ) {
            filteredCategories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        text = TextFieldValue(category)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}