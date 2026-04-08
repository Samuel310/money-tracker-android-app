package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme
import com.zillotrix.moneytracker.core.utils.toDisplayAmount
import com.zillotrix.moneytracker.features.expenses.presentation.ui.common.ExpenseProgressBar

@Composable
fun MonthlyOverallPSAView(
    plannedAmt: Long,
    spentAmt: Long,
    availableAmt: Long,
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        shadowElevation = 10.dp,
        //border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PSASubItem(
                label = "Planned",
                amount = plannedAmt,
                icon = Icons.Outlined.EditCalendar,
            )
            VerticalDivider(
                modifier = Modifier.height(30.dp)
            )
            PSASubItem(
                label = "Spent",
                amount = spentAmt,
                icon = Icons.Outlined.Payments,
            )
            VerticalDivider(
                modifier = Modifier.height(30.dp)
            )
            PSASubItem(
                label = "Available",
                amount = availableAmt,
                icon = Icons.Outlined.AccountBalanceWallet,
            )
        }
    }
}

@Composable
fun PSASubItem(
    label: String,
    amount: Long,
    icon: ImageVector,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Column(

        ) {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                amount.toDisplayAmount(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMonthlyOverallPSAView(){
    MoneyTrackerTheme{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(8.dp)
        ) {
            Column {
                MonthlyOverallPSAView(
                    plannedAmt = 2000,
                    spentAmt = 2000,
                    availableAmt = 2000,
                )
            }
        }
    }
}