package com.example.aceleda_bank.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aceleda_bank.Screen.AccountScreen
import com.example.aceleda_bank.Screen.BalanceScreen
import com.example.aceleda_bank.Screen.CreatedScreen
import com.example.aceleda_bank.Screen.HistoryScreen
import com.example.aceleda_bank.Screen.HomeScreen
import com.example.aceleda_bank.Screen.Pin.PinEntryBalanceScreen
import com.example.aceleda_bank.Screen.Pin.PinEntryQrScreen
import com.example.aceleda_bank.Screen.Pin.PinScanQRScreen
import com.example.aceleda_bank.Screen.QRScannerScreen
import com.example.aceleda_bank.Screen.QR_Code
import com.example.aceleda_bank.Screen.OTP_SCREEN
import com.example.aceleda_bank.Screen.PayScreen
import com.example.aceleda_bank.Screen.PhoneNumber
import com.example.aceleda_bank.Screen.Pin.PinEntryTransferScreen
import com.example.aceleda_bank.Screen.ReEnterPinScreen
import com.example.aceleda_bank.Screen.RegisterScreen
import com.example.aceleda_bank.Screen.SetPinScreen
import com.example.aceleda_bank.Screen.SuccessScreen
import com.example.aceleda_bank.Screen.TransferMethodScreen
import com.example.aceleda_bank.Screen.TransferScreen
import com.example.aceleda_bank.Screen.VerifyScreen
import com.example.aceleda_bank.ViewModel.AuthViewModel
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    // Shared ViewModels for Auth and Transaction flows
    val authViewModel: AuthViewModel = hiltViewModel()
    val transferViewModel: TransactionViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Routes.LOGIN_SCREEN) {
        composable(Routes.PIN_BALANCE) {
            PinEntryBalanceScreen(navController, authViewModel)
        }
        composable(Routes.PIN_QR) {
            PinEntryQrScreen(navController, authViewModel)
        }
        composable(Routes.PIN_SCAN) {
            PinScanQRScreen(navController, authViewModel)
        }
        composable(Routes.PIN_TRANSFER){
            PinEntryTransferScreen(navController, authViewModel)
        }
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.BALANCE){
            BalanceScreen(navController)
        }
        composable(Routes.QR){
            QR_Code(navController)
        }
        composable(Routes.SCAN){
            QRScannerScreen(navController,transferViewModel)
        }
        composable(Routes.OTP_SCREEN) {
            OTP_SCREEN(navController, authViewModel)
        }
        composable(Routes.LOGIN_SCREEN) {
            PhoneNumber(navController, authViewModel)
        }
        composable(Routes.REGISTER_SCREEN) {
            RegisterScreen(authViewModel, navController)
        }
        composable(Routes.PAYSCREEN) {
            PayScreen(navController, transferViewModel)
        }
        composable(Routes.CREATED_SCREEN){
            CreatedScreen(navController, authViewModel)
        }
        composable(Routes.SET_PIN_SCREEN){
            SetPinScreen(navController, authViewModel)
        }
        composable(Routes.SUCCESS_SCREEN){
            SuccessScreen(navController, transferViewModel)
        }
        composable(Routes.CONFIRM_SCREEN){
            ReEnterPinScreen(navController, authViewModel)
        }
        composable(Routes.VERIFY_SCREEN){
            VerifyScreen(navController, transferViewModel)
        }
        composable(Routes.HISTORY_SCREEN){
            HistoryScreen(navController,transferViewModel)
        }
        composable(Routes.TRANSFER){
            TransferScreen(navController, transferViewModel)
        }
        composable(Routes.TRANSFER_METHOD){
            TransferMethodScreen(navController)
        }
        composable(Routes.Account){
            AccountScreen(navController,userViewModel)
        }
    }
}
