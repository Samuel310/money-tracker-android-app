package com.zillotrix.moneytracker.features.budget.data.mapper

import com.zillotrix.moneytracker.features.budget.data.local.entity.IncomeEntity
import com.zillotrix.moneytracker.features.budget.domain.model.Income

fun IncomeEntity.toDomain(): Income {
    return Income(
        id = this.id,
        amount = this.amount,
        date = this.date,
    )
}

fun Income.toEntity(): IncomeEntity {
    return IncomeEntity(
        id = this.id,
        amount = this.amount,
        date = this.date,
    )
}