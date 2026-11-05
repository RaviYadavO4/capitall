package com.portfolio.capitall

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.portfolio.capitall.data.local.CapitallDatabase
import com.portfolio.capitall.data.repository.FinanceRepositoryImpl
import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.model.TransactionType
import com.portfolio.capitall.domain.usecase.AddTransactionUseCase
import com.portfolio.capitall.domain.usecase.GetTransactionsUseCase
import com.portfolio.capitall.presentation.auth.AuthScreen
import com.portfolio.capitall.presentation.auth.BiometricManager
import com.portfolio.capitall.presentation.dashboard.DashboardScreen
import com.portfolio.capitall.presentation.dashboard.DashboardViewModel
import com.portfolio.capitall.presentation.dashboard.DashboardViewModelFactory
import com.portfolio.capitall.ui.theme.BackgroundDark
import com.portfolio.capitall.ui.theme.CapitallTheme
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : FragmentActivity() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var biometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = CapitallDatabase.getDatabase(this)
        val repository = FinanceRepositoryImpl(database.transactionDao)
        val getTransactionsUseCase = GetTransactionsUseCase(repository)
        val addTransactionUseCase = AddTransactionUseCase(repository)

        val factory = DashboardViewModelFactory(getTransactionsUseCase, addTransactionUseCase)
        viewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
        biometricManager = BiometricManager(this)

        // Seed initial premium structural mock transactions data
        lifecycleScope.launch {
            try {
                addTransactionUseCase(Transaction(title = "Stripe Payout: Project Delta", amount = 4250.0, type = TransactionType.INCOME, category = "Freelancing", date = Date()))
                addTransactionUseCase(Transaction(title = "AWS Server Scaling Cloud", amount = 340.0, type = TransactionType.EXPENSE, category = "Infrastructure", date = Date()))
            } catch (_: Exception) {}
        }

        setContent {
            CapitallTheme {
                var isAuthenticated by remember { mutableStateOf(false) }

                fun triggerBiometricAuthentication() {
                    biometricManager.showBiometricPrompt(
                        onSuccess = { isAuthenticated = true },
                        onError = { error ->
                            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Automatic immediate deployment check sequence
                LaunchedEffect(Unit) {
                    triggerBiometricAuthentication()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundDark
                ) {
                    // Ultra-smooth physics crossfade layout interpolation switcher
                    Crossfade(
                        targetState = isAuthenticated,
                        animationSpec = tween(durationMillis = 600),
                        label = "ScreenTransition"
                    ) { authenticated ->
                        if (authenticated) {
                            DashboardScreen(viewModel = viewModel)
                        } else {
                            AuthScreen(
                                onUnlockClick = { triggerBiometricAuthentication() },
                                onTestModeBypass = { isAuthenticated = true } // Directly unlocks for portfolio demonstration
                            )
                        }
                    }
                }
            }
        }
    }
}
