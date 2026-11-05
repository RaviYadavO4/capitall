package com.portfolio.capitall.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portfolio.capitall.domain.model.Transaction
import com.portfolio.capitall.domain.model.TransactionType
import com.portfolio.capitall.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransactionRow(transaction: Transaction) {
    val isIncome = transaction.type == TransactionType.INCOME
    val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())

    // Animation configuration state triggered inside LazyColumns dynamically
    val visibleState = remember {
        MutableTransitionState(false).apply { targetState = true }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = tween(500)) +
                slideInVertically(animationSpec = tween(500), initialOffsetY = { it / 2 })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SurfaceDark)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isIncome) NeonEmerald.copy(alpha = 0.12f) else CrimsonPink.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isIncome) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                    contentDescription = null,
                    tint = if (isIncome) NeonEmerald else CrimsonPink,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    color = TextWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = transaction.category,
                    color = TextGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (isIncome) "+$${transaction.amount}" else "-$${transaction.amount}",
                    color = if (isIncome) NeonEmerald else CrimsonPink,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dateFormat.format(transaction.date),
                    color = TextGray,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
