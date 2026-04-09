package com.zillotrix.moneytracker.features.expenses.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme

@Composable
fun ExpenseProgressBar(
    progress: Float,
    modifier: Modifier,
    shopProgressText: Boolean = true,
){
    Box(
        modifier = modifier,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
        ) {}

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progress),
            color = if (progress >= 1f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium
        ) {}

        if(shopProgressText){
            Text(
                text = "${(progress * 100).toInt()}%",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                color = if (progress > 0.5f) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewExpenseProgressBar(){
    MoneyTrackerTheme{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Column {
                ExpenseProgressBar(progress = 0.9f, modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp))
            }
        }
    }
}