package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zillotrix.moneytracker.core.utils.toMonthName
import com.zillotrix.moneytracker.core.utils.toYearString
import java.time.YearMonth

@Composable
fun CurrentMonthSection(
    currentYearMonth: YearMonth,
    onMonthChanged: (yearMonth: YearMonth) -> Unit,
    onSelectMonthBtnClicked: () -> Unit,
){
    Row(
        modifier = Modifier
            .height(45.dp)
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = currentYearMonth.toMonthName(),
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = currentYearMonth.toYearString(),
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
        Row {
            IconButton(onClick = {
                onMonthChanged(currentYearMonth.minusMonths(1))
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Month"
                )
            }
            OutlinedButton(
                onClick = onSelectMonthBtnClicked
            ) {
                Text("Select Month")
            }
            IconButton(onClick = {
                onMonthChanged(currentYearMonth.plusMonths(1))
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Month"
                )
            }
        }
    }
}