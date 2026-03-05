package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo

@Composable
fun BudgetItem(budgetInfo: BudgetInfo){
    Row(
        modifier = Modifier.height(45.dp).padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = budgetInfo.name,
            modifier = Modifier.padding(end = 8.dp).weight(1f),
        )
        Text(
            text = budgetInfo.amount.toString(),
        )
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go to expenses screen"
            )
        }
    }
}