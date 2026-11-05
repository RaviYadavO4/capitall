package com.portfolio.capitall.domain.model

import java.util.Date

data class Transaction(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: Date
)

enum class TransactionType { INCOME, EXPENSE }
