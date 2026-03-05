package com.zillotrix.moneytracker.features.budget.presentation.ui.common

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
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownField(
    selectedBudgetCategory: BudgetCategory?,
    budgetCategories: List<BudgetCategory>,
    onCategoryChanged: (selectedBudgetCategory: BudgetCategory) -> Unit
) {

    var text by remember { mutableStateOf(TextFieldValue(selectedBudgetCategory?.name ?: "")) }
    var expanded by remember { mutableStateOf(false) }

    val filteredCategories = remember(text.text) {
        if (text.text.isBlank()) budgetCategories
        else budgetCategories.filter {
            it.name.contains(text.text, ignoreCase = true)
        }
    }

    if(text.text.isNotEmpty() && filteredCategories.isEmpty()){
        onCategoryChanged(BudgetCategory(name = text.text, id = 0L))
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
                    text = { Text(category.name) },
                    onClick = {
                        text = TextFieldValue(category.name)
                        expanded = false
                        onCategoryChanged(category)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}