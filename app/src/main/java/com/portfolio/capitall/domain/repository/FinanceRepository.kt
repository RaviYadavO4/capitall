package com.portfolio.capitall.domain.repository

import com.portfolio.capitall.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
}
