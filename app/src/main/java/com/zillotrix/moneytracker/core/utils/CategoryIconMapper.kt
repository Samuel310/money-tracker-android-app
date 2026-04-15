package com.zillotrix.moneytracker.core.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIconMapper {

    private val iconMappings = listOf(
        "Icons.Default.PushPin" to Icons.Default.PushPin,
        "Icons.Default.Style" to Icons.Default.Style,
        "Icons.Default.Savings" to Icons.Default.Savings,
        "Icons.Default.AccountBalance" to Icons.Default.AccountBalance,
        "Icons.Default.Handyman" to Icons.Default.Handyman,
        "Icons.Default.VolunteerActivism" to Icons.Default.VolunteerActivism,
        "Icons.Default.AutoStories" to Icons.Default.AutoStories
    )

    val DEFAULT_ICON = Icons.Default.Category
    const val DEFAULT_KEY = "Icons.Default.Category"

    fun getIcon(iconKey: String): ImageVector {
        return iconMappings.find { it.first == iconKey }?.second ?: DEFAULT_ICON
    }

    fun getIconKey(icon: ImageVector): String {
        return iconMappings.find { it.second == icon }?.first ?: DEFAULT_KEY
    }
}