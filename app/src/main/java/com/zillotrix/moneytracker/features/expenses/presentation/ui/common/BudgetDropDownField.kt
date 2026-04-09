package com.zillotrix.moneytracker.features.expenses.presentation.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zillotrix.moneytracker.features.budget.domain.model.Budget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDropDownField(
    selectedBudget: Budget?,
    budgetList: List<Budget>,
    onBudgetChanged: (budget: Budget) -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedBudget?.name ?: "Select a budget",
            onValueChange = {}, // Read-only, so empty
            readOnly = true,
            label = { Text("Budget") },
            trailingIcon = {
                // Standard arrow icon that rotates when expanded
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            budgetList.forEach { budget ->
                DropdownMenuItem(
                    text = { Text(budget.name) },
                    onClick = {
                        onBudgetChanged(budget)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
