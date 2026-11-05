package com.portfolio.capitall.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.portfolio.capitall.domain.usecase.AddTransactionUseCase
import com.portfolio.capitall.domain.usecase.GetTransactionsUseCase

class DashboardViewModelFactory(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(getTransactionsUseCase, addTransactionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
