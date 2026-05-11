package com.example.aceleda_bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.aceleda_bank.Screen.HomeScreen
import com.example.aceleda_bank.ui.theme.Aceleda_BankTheme
import com.example.pinentry.PinEntryScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pinentry.PinEntryScreenPreview
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Aceleda_BankTheme {
                val navController = rememberNavController()  // 👈
                NavHost(navController = navController, startDestination = "pin") {
                    composable("pin") {
                        PinEntryScreenPreview(navController)  // 👈
                    }
                    composable("home") {
                        HomeScreen()  // 👈
                    }
                }
            }
        }
    }
}