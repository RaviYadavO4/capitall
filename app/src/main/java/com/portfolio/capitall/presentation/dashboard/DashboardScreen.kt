package com.portfolio.capitall.presentation.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portfolio.capitall.presentation.components.AnalyticsChart
import com.portfolio.capitall.presentation.components.TransactionRow
import com.portfolio.capitall.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // FAB Custom Scale Physics Interactivity State for Spring Effect
    var isFabPressed by remember { mutableStateOf(false) }
    val fabScale by animateFloatAsState(
        targetValue = if (isFabPressed) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        finishedListener = { if (isFabPressed) isFabPressed = false },
        label = "FabSpringEffect"
    )

    Scaffold(
        containerColor = BackgroundDark,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isFabPressed = true
                    showBottomSheet = true
                },
                containerColor = AccentPurple,
                contentColor = TextWhite,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.scale(fabScale)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                // Header Section with Integrated Test Mode Dynamic Data Injector
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Welcome Back,", color = TextGray, fontSize = 14.sp)
                            Text(
                                text = "Track your Wealth",
                                color = TextWhite,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Developer Sandbox Token Button for Client Screen Recording Presentations
                        Text(
                            text = "[Inject Data]",
                            color = AccentPurple,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceDark)
                                .clickable { viewModel.injectMockData() }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Balance Card Matrix with Ultra-Fluid Slide Animated Numbers
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(AccentPurple, AccentPurple.copy(alpha = 0.5f))
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column {
                                Text(
                                    text = "TOTAL BALANCE",
                                    color = TextWhite.copy(alpha = 0.6f),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                AnimatedContent(
                                    targetState = uiState.totalBalance,
                                    transitionSpec = {
                                        slideInVertically { it } + fadeIn() togetherWith slideOutVertically { -it } + fadeOut()
                                    },
                                    label = "BalanceAnim"
                                ) { balance ->
                                    Text(
                                        text = "$${String.format("%.2f", balance)}",
                                        color = TextWhite,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = "Income", color = TextWhite.copy(alpha = 0.6f), fontSize = 11.sp)
                                    AnimatedContent(
                                        targetState = uiState.totalIncome,
                                        transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() },
                                        label = "IncomeAnim"
                                    ) { income ->
                                        Text(
                                            text = "$${String.format("%.1f", income)}",
                                            color = NeonEmerald,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Column {
                                    Text(text = "Expenses", color = TextWhite.copy(alpha = 0.6f), fontSize = 11.sp)
                                    AnimatedContent(
                                        targetState = uiState.totalExpense,
                                        transitionSpec = { scaleIn() + fadeIn() togetherWith scaleOut() + fadeOut() },
                                        label = "ExpenseAnim"
                                    ) { expense ->
                                        Text(
                                            text = "$${String.format("%.1f", expense)}",
                                            color = CrimsonPink,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Custom Canvas Drawing Chart with Wave Progress Interpolation
                item {
                    val chartData = if (uiState.transactions.isEmpty()) {
                        listOf(0f, 0f, 0f)
                    } else {
                        uiState.transactions.map { it.amount.toFloat() }.take(7).reversed()
                    }
                    AnalyticsChart(points = chartData)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Transactions Header Section
                item {
                    Text(
                        text = "Recent Transactions",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (uiState.transactions.isEmpty()) {
                        Text(
                            text = "No recent data available. Tap '+' to create.",
                            color = TextGray,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }
                }
// Scrollable List of Actual Room Database Elements with Unique Keys
                items(uiState.transactions, key = { it.id }) { transaction ->
                    TransactionRow(transaction = transaction)
                }
            }
// Display BottomSheet Form overlay dynamically when state is triggered active
            if (showBottomSheet) {
                AddTransactionBottomSheet(
                    onDismiss = { showBottomSheet = false },
                    onSave = { title, amount, type, category ->
                        viewModel.addTransaction(title, amount, type, category)
                    }
                )
            }
        }
    }
}