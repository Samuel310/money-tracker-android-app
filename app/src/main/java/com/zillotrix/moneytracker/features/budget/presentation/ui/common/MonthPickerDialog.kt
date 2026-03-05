package com.zillotrix.moneytracker.features.budget.presentation.ui.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zillotrix.moneytracker.core.ui.theme.MoneyTrackerTheme
import java.time.YearMonth

@Composable
fun MonthPickerDialog(
    selectedYearMonth: YearMonth?,
    onDismiss: (yearMonth: YearMonth?) -> Unit,
){
    val months = listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
    )

    var month by remember { mutableStateOf(months[(selectedYearMonth?.monthValue ?: YearMonth.now().monthValue) - 1]) }

    var year by remember { mutableIntStateOf(selectedYearMonth?.year ?: YearMonth.now().year) }

    val interactionSource = remember { MutableInteractionSource() }

    Dialog(onDismissRequest = { onDismiss(null) }) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
                        year--
                    }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous Month"
                        )
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = year.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = {
                        year++
                    }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next Month"
                        )
                    }

                }


                Card(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    ),
                ) {

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        months.forEach {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = interactionSource,
                                        onClick = {
                                            month = it
                                        }
                                    )
                                    .background(
                                        color = Color.Transparent
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                val animatedSize by animateDpAsState(
                                    targetValue = if (month == it) 60.dp else 0.dp,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing
                                    )
                                )

                                Box(
                                    modifier = Modifier
                                        .size(animatedSize)
                                        .background(
                                            color = if (month == it) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            shape = CircleShape
                                        )
                                )

                                Text(
                                    text = it,
                                    color = if (month == it) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium
                                )

                            }
                        }

                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    OutlinedButton(
                        modifier = Modifier.padding(end = 16.dp),
                        onClick = {
                            onDismiss(YearMonth.of(year, months.indexOf(month) + 1))
                        },
                        shape = CircleShape,
                    ) {
                        Text(
                            text = "Cancel",
                        )

                    }

                    Button(
                        onClick = {
                            onDismiss(YearMonth.of(year, months.indexOf(month) + 1))
                        }
                    ) {
                        Text("OK")
                    }

                }

            }
        }
    }
}

@Preview(name = "Month Picker Dialog", showBackground = true)
@Composable
fun MonthPickerDialogPreview(){
    MoneyTrackerTheme(darkTheme = false, dynamicColor = false){
        Column(modifier = Modifier.padding(16.dp)) {
            MonthPickerDialog(selectedYearMonth = YearMonth.now(), onDismiss = {})
        }
    }
}