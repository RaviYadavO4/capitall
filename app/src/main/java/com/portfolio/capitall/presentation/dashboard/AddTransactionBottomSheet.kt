package com.portfolio.capitall.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portfolio.capitall.domain.model.TransactionType
import com.portfolio.capitall.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(
    onDismiss: () -> Unit,
    onSave: (title: String, amount: Double, type: TransactionType, category: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var category by remember { mutableStateOf("General") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceDark,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding() // Ensures navigation bar spacing
        ) {
            Text(
                text = "New Transaction",
                color = TextWhite,
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Income / Expense Toggle Segment
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(BackgroundDark)
                    .padding(4.dp)
            ) {
                Button(
                    onClick = { selectedType = TransactionType.EXPENSE },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == TransactionType.EXPENSE) CrimsonPink else Color.Transparent
                    ),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Expense", color = TextWhite)
                }
                Button(
                    onClick = { selectedType = TransactionType.INCOME },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == TransactionType.INCOME) NeonEmerald else Color.Transparent
                    ),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Income", color = TextWhite)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Fields
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = TextGray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPurple,
                    unfocusedBorderColor = SurfaceDark,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount ($)", color = TextGray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPurple,
                    unfocusedBorderColor = SurfaceDark,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category", color = TextGray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentPurple,
                    unfocusedBorderColor = SurfaceDark,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Action Button
            Button(
                onClick = {
                    val parsedAmount = amount.toDoubleOrNull() ?: 0.0
                    if (title.isNotBlank() && parsedAmount > 0.0) {
                        onSave(title, parsedAmount, selectedType, category)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Transaction", color = TextWhite, fontSize = 16.sp)
            }
        }
    }
}
