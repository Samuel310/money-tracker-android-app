package com.zillotrix.moneytracker.features.budget.presentation.components.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BudgetItem(){
    Row(
        modifier = Modifier.height(45.dp).padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Home",
            modifier = Modifier.padding(end = 8.dp),
        )
        Text(
            text = "5000",
            modifier = Modifier.weight(1f),
        )
        Checkbox(
            checked = false,
            onCheckedChange = {

            }
        )
    }
}