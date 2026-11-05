package com.portfolio.capitall.domain.usecase

import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(
    private val repository: FinanceRepository
) {
    operator fun invoke(): Flow<List<Transaction>> {
        return repository.getAllTransactions()
    }
}
