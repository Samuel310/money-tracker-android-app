package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.dto.CategoryTotalDTO
import com.zillotrix.moneytracker.features.budget.domain.model.CategoryTotal


fun CategoryTotalDTO.toDomain(percentage: Double): CategoryTotal {
    return CategoryTotal(
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        totalAmount = this.totalAmount,
        percentage = percentage,
    )
}

fun CategoryTotal.toDTO(): CategoryTotalDTO {
    return CategoryTotalDTO(
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        totalAmount = this.totalAmount,
    )
}