package com.portfolio.capitall.data.repository

import com.portfolio.capitall.data.local.TransactionDao
import com.portfolio.capitall.data.local.toTransaction
import com.portfolio.capitall.data.local.toTransactionEntity
import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FinanceRepositoryImpl(
    private val dao: TransactionDao
) : FinanceRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { entities ->
            entities.map { it.toTransaction() }
        }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toTransactionEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction.toTransactionEntity())
    }
}
