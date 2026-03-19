package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableFab(
    isExpanded: Boolean,
    onMainFabClick: () -> Unit,
    onAddBudgetClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SmallFloatingActionButton(
                    onClick = onAddBudgetClick,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Default.Add, contentDescription = "New Budget")
                }
                Text(
                    "New Budget",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom),
            exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SmallFloatingActionButton(
                    onClick = onAddExpenseClick,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Default.Receipt, contentDescription = "New Expense")
                }
                Text(
                    "New Expense",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        FloatingActionButton(
            onClick = onMainFabClick,
            containerColor = if (isExpanded)
                MaterialTheme.colorScheme.tertiaryContainer
            else
                MaterialTheme.colorScheme.primaryContainer
        ) {
            val rotation by animateFloatAsState(
                targetValue = if (isExpanded) 45f else 0f,
                label = "FabRotation"
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Main Menu",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}