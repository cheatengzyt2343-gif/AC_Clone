package com.example.aceleda_bank.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val NavyDark   = Color(0xFF0D2B5E)
private val HintGray   = Color(0xFF888888)
private val FieldBorder= Color(0xFFDDDDDD)
private val NavyLight  = Color(0xFF3A6BC4)

@Composable
fun acledaTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor   = NavyLight,
    unfocusedBorderColor = FieldBorder,
    focusedTextColor     = Color(0xFF1A1A2E),
    unfocusedTextColor   = Color(0xFF1A1A2E),
    cursorColor          = NavyDark,
    focusedContainerColor   = Color.White,
    unfocusedContainerColor = Color.White
)

@Composable
fun FieldLabel(text: String) {
    Text(
        text     = text,
        color    = HintGray,
        fontSize = 12.sp
    )
}

@Composable
fun ShieldIcon() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(160.dp)
    ) {

        // Outer ring
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            NavyLight.copy(alpha = 0.3f),
                            NavyDark
                        )
                    )
                )
        )

        // Middle ring
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            NavyLight.copy(alpha = 0.5f),
                            NavyDark
                        )
                    )
                )
        )

        // Inner circle with lock icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(NavyLight)
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Security Shield",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}