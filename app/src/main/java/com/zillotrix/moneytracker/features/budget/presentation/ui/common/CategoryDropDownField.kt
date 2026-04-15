package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.zillotrix.moneytracker.core.utils.CategoryIconMapper
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
    val focusRequester = remember { FocusRequester() }

    val filteredCategories = remember(text.text, budgetCategories) {
        if (text.text.isBlank()) {
            budgetCategories
        } else budgetCategories.filter {
            it.name.contains(text.text, ignoreCase = true)
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        kotlinx.coroutines.delay(100)
        expanded = true
        println(filteredCategories.size)
    }

    LaunchedEffect(text.text, filteredCategories) {
        if (text.text.isNotEmpty() && filteredCategories.isEmpty()) {
            onCategoryChanged(
                BudgetCategory(
                    name = text.text,
                    id = 0L,
                    info = "Custom category",
                    icon = CategoryIconMapper.DEFAULT_ICON,
                    color = Color.Gray,
                    isDefault = false,
                )
            )
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        expanded = true
                    }
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
            onDismissRequest = {
                expanded = true
            },
            properties = PopupProperties(focusable = false),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 280.dp)
        ) {
            filteredCategories.forEach { category ->
                DropdownMenuItem(
                    leadingIcon = {
                        Surface(
                            shape = CircleShape,
                            color = category.color.copy(alpha = 0.1f),
                        ) {
                            Box(
                                modifier = Modifier.padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = category.icon,
                                    contentDescription = null,
                                    tint = category.color,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    text = {
                        Column {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = category.info,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
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