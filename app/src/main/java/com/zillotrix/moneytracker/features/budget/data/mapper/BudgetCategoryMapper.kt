package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.core.utils.CategoryIconMapper
import com.zillotrix.moneytracker.core.utils.toComposeColor
import com.zillotrix.moneytracker.core.utils.toHexCode
import com.zillotrix.moneytracker.features.budget.data.local.entity.BudgetCategoryEntity
import com.zillotrix.moneytracker.features.budget.domain.model.BudgetCategory


fun BudgetCategoryEntity.toDomain(): BudgetCategory {
    return BudgetCategory(
        id = this.id,
        name = this.name,
        info = this.info,
        icon = CategoryIconMapper.getIcon(this.icon),
        color = this.color.toComposeColor(),
        isDefault = this.isDefault,
    )
}

fun BudgetCategory.toEntity(): BudgetCategoryEntity {
    return BudgetCategoryEntity(
        id = this.id,
        name = this.name,
        info = this.info,
        icon = CategoryIconMapper.getIconKey(this.icon),
        color = this.color.toHexCode(),
        isDefault = false,
    )
}