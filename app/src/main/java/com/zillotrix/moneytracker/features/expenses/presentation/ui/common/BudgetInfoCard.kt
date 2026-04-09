package com.zillotrix.moneytracker.features.expenses.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zillotrix.moneytracker.core.navigation.LocalNavActions
import com.zillotrix.moneytracker.core.utils.toDisplayAmount
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetInfo

@Composable
fun BudgetInfoCard(
    budgetInfo: BudgetInfo?,
    clickable : Boolean = true,
    shadowElevation: Dp = 0.dp
){

    val navActions = LocalNavActions.current

    if(budgetInfo == null){
        return
    }

    val plannedAmt = budgetInfo.amount
    val totalAmtSpent = budgetInfo.totalAmtSpent
    val remainingAmount = plannedAmt - totalAmtSpent
    val progress = if (plannedAmt > 0L) {
        (totalAmtSpent / plannedAmt.toFloat()).coerceIn(0f, 1f)
    } else 0f


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(
                if (clickable) {
                    Modifier.Companion.clickable {
                        navActions.navigateToExpenseScreen(budgetInfo.id, budgetInfo.yearMonth)
                    }
                } else {
                    Modifier
                }
            ),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 0.dp,
        shadowElevation = shadowElevation,
        border = BorderStroke(0.5.dp, Color.Companion.LightGray.copy(alpha = 0.3f)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.Bottom
            ) {
                Column {
                    Text(
                        text = "Spent",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = totalAmtSpent.toDisplayAmount(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Companion.SemiBold),
                        color = if (progress >= 1f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
                Column(horizontalAlignment = Alignment.Companion.End) {
                    Text(
                        text = "Available",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = remainingAmount.toDisplayAmount(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Companion.SemiBold),
                        color = if (remainingAmount < 0) MaterialTheme.colorScheme.error else Color(
                            0xFF4CAF50
                        ) // Green for healthy budget
                    )
                }
            }

            ExpenseProgressBar(
                progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            )

            Text(
                text = "of ${plannedAmt.toDisplayAmount()} planned",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.Companion.End)
            )
        }
    }
}