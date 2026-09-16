package com.example.aceleda_bank.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PinKeypad(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("backspace", "0", "done")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    PinKey(
                        key = key,
                        onClick = {
                            when (key) {
                                "backspace" -> onBackspaceClick()
                                "done" -> onDoneClick()
                                else -> onNumberClick(key)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PinKey(
    key: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when (key) {
            "backspace" -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Backspace,
                    contentDescription = "Backspace",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            "done" -> {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8AB4F8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = Color(0xFF202124),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            else -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = key,
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal
                    )
                    val letters = getLettersForKey(key)
                    if (letters.isNotEmpty()) {
                        Text(
                            text = letters,
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

private fun getLettersForKey(key: String): String {
    return when (key) {
        "2" -> "ABC"
        "3" -> "DEF"
        "4" -> "GHI"
        "5" -> "JKL"
        "6" -> "MNO"
        "7" -> "PQRS"
        "8" -> "TUV"
        "9" -> "WXYZ"
        else -> ""
    }
}
