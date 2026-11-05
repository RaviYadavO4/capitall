package com.portfolio.capitall.presentation.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portfolio.capitall.ui.theme.*
import androidx.compose.foundation.BorderStroke

@Composable
fun AuthScreen(
    onUnlockClick: () -> Unit,
    onTestModeBypass: () -> Unit // New callback function
) {
    val infiniteTransition = rememberInfiniteTransition(label = "GlowPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.22f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "CAPITALL",
                color = TextWhite,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 6.sp
            )
            Text(
                text = "SECURE PROTOCOL ACTIVE",
                color = AccentPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .scale(pulseScale)
                        .border(
                            width = 2.dp,
                            brush = Brush.radialGradient(
                                colors = listOf(AccentPurple.copy(alpha = 0.4f), Color.Transparent)
                            ),
                            shape = CircleShape
                        )
                )

                IconButton(
                    onClick = onUnlockClick,
                    modifier = Modifier
                        .size(84.dp)
                        .background(SurfaceDark, CircleShape)
                        .border(1.dp, AccentPurple.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Verify Identity",
                        tint = AccentPurple,
                        modifier = Modifier.size(42.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Tap scanner to authorize securely",
                color = TextGray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Premium Test Mode Bypass Button
            OutlinedButton(
                onClick = onTestModeBypass,
                border = BorderStroke(1.dp, AccentPurple.copy(alpha = 0.4f)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = SurfaceDark.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(44.dp)
            ) {
                Text(
                    text = "Bypass with Test Mode",
                    color = AccentPurple,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
