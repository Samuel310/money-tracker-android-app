package com.zillotrix.moneytracker.features.base.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zillotrix.moneytracker.features.budget.presentation.ui.BudgetScreen

@Composable
fun BaseScreen(onNavigateToNewBudgetScreen : () -> Unit){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            BudgetScreen(onNavigateToNewBudgetScreen)
        }
    }
}