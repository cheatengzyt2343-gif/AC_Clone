package com.example.aceleda_bank.Screen.Pin

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.ViewModel.AuthViewModel
import com.example.pinentry.PinEntryScreen

@Composable
fun PinScanQRScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    PinEntryScreen(
        viewModel = viewModel,
        navController=navController,
        onSuccess = { navController.navigate(Routes.SCAN) },
        onError = { Toast.makeText(context, "no", Toast.LENGTH_SHORT).show() }
    )
}