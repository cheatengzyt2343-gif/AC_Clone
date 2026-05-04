package com.example.aceleda_bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.aceleda_bank.Screen.HomeScreen
import com.example.aceleda_bank.ui.theme.Aceleda_BankTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Aceleda_BankTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }
}