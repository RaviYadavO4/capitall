package com.portfolio.capitall.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.model.TransactionType
import com.portfolio.capitall.domain.usecase.AddTransactionUseCase
import com.portfolio.capitall.domain.usecase.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardUiState(
    val transactions: List<Transaction> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)

class DashboardViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase().collect { list ->
                var income = 0.0
                var expense = 0.0

                list.forEach {
                    if (it.type == TransactionType.INCOME) income += it.amount
                    else expense += it.amount
                }

                _uiState.value = DashboardUiState(
                    transactions = list,
                    totalIncome = income,
                    totalExpense = expense,
                    totalBalance = income - expense
                )
            }
        }
    }

    fun addTransaction(title: String, amount: Double, type: TransactionType, category: String) {
        viewModelScope.launch {
            try {
                val newTransaction = Transaction(
                    title = title,
                    amount = amount,
                    type = type,
                    category = category,
                    date = java.util.Date()
                )
                addTransactionUseCase(newTransaction)
            } catch (e: Exception) {
                // Handle UI Error State here if needed
            }
        }
    }

    // Random mock records generation for testing and client presentation demos
    fun injectMockData() {
        viewModelScope.launch {
            val titles = listOf("Upwork Milestone", "Fiverr Order", "AWS Server Bill", "Coffee Tech Meet", "Gym Subscription")
            val amounts = listOf(1500.0, 450.0, 85.0, 15.0, 60.0)
            val types = listOf(TransactionType.INCOME, TransactionType.INCOME, TransactionType.EXPENSE, TransactionType.EXPENSE, TransactionType.EXPENSE)
            val categories = listOf("Freelancing", "Gigs", "Cloud Infrastructure", "Networking", "Fitness")

            val randomIndex = (0..4).random()

            val mockTransaction = Transaction(
                title = titles[randomIndex],
                amount = amounts[randomIndex],
                type = types[randomIndex],
                category = categories[randomIndex],
                date = java.util.Date()
            )
            addTransactionUseCase(mockTransaction)
        }
    }
}
