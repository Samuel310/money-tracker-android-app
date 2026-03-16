package com.zillotrix.moneytracker.features.expenses.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zillotrix.moneytracker.core.utils.toDayMonthYearFull
import com.zillotrix.moneytracker.features.expenses.domain.model.Expense

@Composable
fun ExpenseItem(expense: Expense, onNavigateToNewExpenseScreen: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onNavigateToNewExpenseScreen),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expense.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = expense.date.toDayMonthYearFull(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "₹${expense.amount}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}