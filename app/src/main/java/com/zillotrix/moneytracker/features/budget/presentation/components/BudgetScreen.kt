package com.zillotrix.moneytracker.features.budget.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zillotrix.moneytracker.core.navigation.Screen
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme
import com.zillotrix.moneytracker.features.budget.presentation.components.common.BudgetItem

@Composable
fun BudgetScreen(navController: NavHostController){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.NewBudgetScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Budget"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.height(45.dp).padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "January",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                Button(onClick = {}) {
                    Text("Change Month")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.height(45.dp).padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Income: ",
                )
                Text(
                    text = "5000",
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Change month"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Budget",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text("Contribution",  fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 8.dp, end = 8.dp))
            BudgetItem()
            BudgetItem()
            BudgetItem()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBudgetScreen(){
    val navController = rememberNavController()
    MoneyTrackerTheme{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            BudgetScreen(navController)
        }
    }
}