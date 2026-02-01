package com.zillotrix.moneytracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.zillotrix.moneytracker.core.navigation.AppNavGraph
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme

@Composable
fun App(){
    val navController = rememberNavController()
    MoneyTrackerTheme{
        AppNavGraph(navController)
    }
}