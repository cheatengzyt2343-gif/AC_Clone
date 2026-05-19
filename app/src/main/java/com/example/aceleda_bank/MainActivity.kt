package com.example.aceleda_bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.aceleda_bank.Screen.HomeScreen
import com.example.aceleda_bank.ui.theme.Aceleda_BankTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aceleda_bank.Screen.BalanceScreen
import com.example.pinentry.PinEntryScreenPreview
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Aceleda_BankTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("pin") {
                        PinEntryScreenPreview(navController)
                    }
                    composable("home") {
                        HomeScreen(navController)
                    }
                    composable("balance"){
                        BalanceScreen(navController);
                    }
                }
            }
        }
    }
}