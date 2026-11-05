package com.portfolio.capitall.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portfolio.capitall.ui.theme.*

@Composable
fun AnalyticsChart(
    modifier: Modifier = Modifier,
    points: List<Float> = listOf(20f, 40f, 35f, 60f, 55f, 80f, 70f)
) {
    // Animation driver for drawing line sequentially from left to right
    var animationTriggered by remember { mutableStateOf(false) }
    val lineProgress by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
        label = "LineDrawProgress"
    )

    LaunchedEffect(Unit) {
        animationTriggered = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(SurfaceDark)
            .padding(20.dp)
    ) {
        Text(
            text = "Weekly Activity Trend",
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            val width = size.width
            val height = size.height
            val spacing = width / (points.size - 1)

            val maxPoint = points.maxOrNull() ?: 1f
            val minPoint = points.minOrNull() ?: 0f
            val range = if (maxPoint - minPoint == 0f) 1f else maxPoint - minPoint

            val path = Path()
            val gradientPath = Path()

            // Calculate animated bounds based on progress factor
            val maxRenderedIndex = ((points.size - 1) * lineProgress).toInt()

            points.forEachIndexed { index, point ->
                if (index <= maxRenderedIndex + 1 && index < points.size) {
                    val x = index * spacing
                    val y = height - ((point - minPoint) / range * height)

                    // Interpolate coordinates seamlessly for the running segment
                    val currentX = if (index > maxRenderedIndex) {
                        val prevX = (index - 1) * spacing
                        val segmentProgress = ((points.size - 1) * lineProgress) - maxRenderedIndex
                        prevX + (x - prevX) * segmentProgress
                    } else x

                    val currentY = if (index > maxRenderedIndex) {
                        val prevY = height - (((points[index - 1] - minPoint) / range) * height)
                        val segmentProgress = ((points.size - 1) * lineProgress) - maxRenderedIndex
                        prevY + (y - prevY) * segmentProgress
                    } else y

                    if (index == 0) {
                        path.moveTo(currentX, currentY)
                        gradientPath.moveTo(currentX, height)
                        gradientPath.lineTo(currentX, currentY)
                    } else {
                        path.lineTo(currentX, currentY)
                        gradientPath.lineTo(currentX, currentY)
                    }

                    if (index == maxRenderedIndex || index == points.size - 1) {
                        gradientPath.lineTo(currentX, height)
                        gradientPath.close()
                    }

                    if (index <= maxRenderedIndex) {
                        drawCircle(
                            color = AccentPurple,
                            radius = 4.dp.toPx(),
                            center = Offset(x, y)
                        )
                    }
                }
            }

            drawPath(
                path = path,
                color = AccentPurple,
                style = Stroke(width = 3.dp.toPx())
            )

            drawPath(
                path = gradientPath,
                brush = Brush.verticalGradient(
                    colors = listOf(AccentPurple.copy(alpha = 0.25f), Color.Transparent)
                )
            )
        }
    }
}
