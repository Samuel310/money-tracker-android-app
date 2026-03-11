package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo

@Composable
fun BudgetItem(budgetInfo: BudgetInfo){

    // Calculate progress (clamped between 0 and 1)
    val progress = if (budgetInfo.amount > 0L) {
        (budgetInfo.totalAmtSpent / budgetInfo.amount.toFloat()).coerceIn(0f, 1f)
    } else 0f

    val remainingAmount = budgetInfo.amount - budgetInfo.totalAmtSpent


    Column(
        modifier = Modifier
            .height(60.dp)
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
            ) {
                Text(
                    text = budgetInfo.name,
                    fontSize = 18.sp
                )
                Text(
                    text = "Remaining ₹${remainingAmount}",
                    fontSize = 14.sp
                )
            }
            Text(
                text = "₹${budgetInfo.amount}",
                fontSize = 18.sp
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to expenses screen"
                )
            }
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            strokeCap = StrokeCap.Round, // Makes the ends rounded
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            color = if (progress >= 1f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBudgetItem(){
    MoneyTrackerTheme{
        Surface(
            modifier = Modifier
                .systemBarsPadding()
        ) {
            Column {
                BudgetItem(BudgetInfo(
                    id = 1,
                    name = "Stock",
                    amount = 500,
                    categoryId = 1,
                    categoryName = "qwerty",
                    monthYear = 202603,
                    totalAmtSpent = 300
                ))
                BudgetItem(BudgetInfo(
                    id = 1,
                    name = "Stock",
                    amount = 600,
                    categoryId = 1,
                    categoryName = "qwerty",
                    monthYear = 202603,
                    totalAmtSpent = 200,
                ))
            }
        }
    }
}