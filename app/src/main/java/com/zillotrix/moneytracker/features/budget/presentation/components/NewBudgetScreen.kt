package com.zillotrix.moneytracker.features.budget.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme
import com.zillotrix.moneytracker.features.budget.presentation.components.common.CategoryDropdownField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBudgetScreen(navController: NavHostController) {

    // ---------- UI state ----------
    var budgetName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var monthYear by remember { mutableStateOf("Jan 2026") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Budget") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )

        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /* ---------------- Category Dropdown ---------------- */

            CategoryDropdownField()

            /* ---------------- Budget Name ---------------- */

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = budgetName,
                onValueChange = {budgetName = it },
                label = { Text("Budget Name") },
                placeholder = { Text("e.g. Monthly Groceries") }
            )

            /* ---------------- Amount ---------------- */

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Text("₹") }
            )

            /* ---------------- Month-Year ---------------- */

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = monthYear,
                onValueChange = {},
                label = { Text("Month & Year") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Pick Month"
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------------- Save Button ---------------- */

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Save")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNewBudgetScreen(){
    val navController = rememberNavController()
    MoneyTrackerTheme{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            NewBudgetScreen(navController)
        }
    }
}