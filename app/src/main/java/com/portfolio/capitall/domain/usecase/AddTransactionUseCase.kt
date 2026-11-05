package com.portfolio.capitall.domain.usecase

import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.repository.FinanceRepository

class AddTransactionUseCase(
    private val repository: FinanceRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        if (transaction.title.isBlank() || transaction.amount <= 0) {
            throw IllegalArgumentException("Invalid Title or Amount")
        }
        repository.insertTransaction(transaction)
    }
}
