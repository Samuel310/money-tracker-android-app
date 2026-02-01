package com.zillotrix.moneytracker.features.base.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.zillotrix.moneytracker.features.budget.presentation.components.BudgetScreen

@Composable
fun BaseScreen(navController: NavHostController){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            BudgetScreen(navController)
        }
    }
}