package com.portfolio.capitall.data.local

import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.model.TransactionType
import java.util.Date

fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        id = id,
        title = title,
        amount = amount,
        type = if (type == "INCOME") TransactionType.INCOME else TransactionType.EXPENSE,
        category = category,
        date = Date(timestamp)
    )
}

fun Transaction.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        type = type.name,
        category = category,
        timestamp = date.time
    )
}
